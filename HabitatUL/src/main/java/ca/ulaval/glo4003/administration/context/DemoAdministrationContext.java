package ca.ulaval.glo4003.administration.context;

import ca.ulaval.glo4003.administration.application.user.AccessController;
import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.communication.user.UserBoundedContextEventHandler;
import ca.ulaval.glo4003.administration.domain.user.*;
import ca.ulaval.glo4003.administration.domain.user.credential.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManager;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.administration.infrastructure.user.ConfigBasedTokenValidityPeriodProvider;
import ca.ulaval.glo4003.administration.infrastructure.user.DummyPasswordManager;
import ca.ulaval.glo4003.administration.infrastructure.user.DummyPaymentProcessor;
import ca.ulaval.glo4003.administration.infrastructure.user.JwtTokenTranslator;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryPolicyRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryQuoteRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryTokenRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryUsernameRegistry;
import ca.ulaval.glo4003.mediator.Mediator;

import java.util.Properties;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoAdministrationContext {
  public void execute(Properties properties, Mediator mediator) {
    InMemoryUsernameRegistry usernameRegistry = new InMemoryUsernameRegistry();
    PasswordManager passwordManager = new DummyPasswordManager();
    registerAdminUser(properties, usernameRegistry, passwordManager);

    register(PasswordManager.class, passwordManager);
    register(PaymentProcessor.class, new DummyPaymentProcessor());
    register(PolicyRegistry.class, new InMemoryPolicyRegistry());
    register(QuoteRegistry.class, new InMemoryQuoteRegistry());
    register(UsernameRegistry.class, usernameRegistry);
    register(TokenRegistry.class, new InMemoryTokenRegistry());
    String jwtSecret = String.valueOf(properties.getProperty("jwt.secret"));
    register(TokenTranslator.class, new JwtTokenTranslator(jwtSecret));
    register(TokenValidityPeriodProvider.class, new ConfigBasedTokenValidityPeriodProvider());

    UserAppService userAppService = new UserAppService();
    register(AccessController.class, userAppService);
    UserBoundedContextEventHandler userBoundedContextEventHandler =
        new UserBoundedContextEventHandler(userAppService);
    userBoundedContextEventHandler.register(mediator);
  }

  private void registerAdminUser(
      Properties properties,
      UsernameRegistry usernameRegistry,
      PasswordManager passwordManager) {
    String adminKey = String.valueOf(properties.getProperty("admin.key"));
    String adminName = String.valueOf(properties.getProperty("admin.username"));
    String adminPassword = String.valueOf(properties.getProperty("admin.password"));
    try {
      usernameRegistry.register(adminKey, adminName);
      passwordManager.registerPassword(adminName, adminPassword);
    } catch (KeyAlreadyExistException | InvalidPasswordException e) {
      e.printStackTrace();
    }
  }
}
