package ca.ulaval.glo4003.administration.context;

import ca.ulaval.glo4003.administration.application.user.AccessController;
import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.application.user.UserAppServiceImpl;
import ca.ulaval.glo4003.administration.application.user.UserAppServiceLoggingDecorator;
import ca.ulaval.glo4003.administration.communication.user.UserBoundedContextEventHandler;
import ca.ulaval.glo4003.administration.domain.user.*;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManager;
import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.administration.infrastructure.user.DummyPaymentProcessor;
import ca.ulaval.glo4003.administration.infrastructure.user.credential.InMemoryPbkdfPasswordStorage;
import ca.ulaval.glo4003.administration.infrastructure.user.credential.PbkdfPasswordManager;
import ca.ulaval.glo4003.administration.infrastructure.user.token.ConfigBasedTokenValidityPeriodProvider;
import ca.ulaval.glo4003.administration.infrastructure.user.token.JwtTokenTranslator;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryPolicyRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryQuoteRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryTokenRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryUsernameRegistry;
import ca.ulaval.glo4003.mediator.Mediator;

import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoAdministrationContext {
  public void execute(Properties properties, Mediator mediator) {
    InMemoryUsernameRegistry usernameRegistry = new InMemoryUsernameRegistry();
    PasswordManager passwordManager = createPasswordManager();
    registerAdminUser(properties, usernameRegistry, passwordManager);
    registerActuaryUser(properties, usernameRegistry, passwordManager);

    register(PasswordManager.class, passwordManager);
    register(PaymentProcessor.class, new DummyPaymentProcessor());
    register(PolicyRegistry.class, new InMemoryPolicyRegistry());
    register(QuoteRegistry.class, new InMemoryQuoteRegistry());
    register(UsernameRegistry.class, usernameRegistry);
    register(TokenRegistry.class, new InMemoryTokenRegistry());
    String jwtSecret = String.valueOf(properties.getProperty("jwt.secret"));
    register(TokenTranslator.class, new JwtTokenTranslator(jwtSecret));
    register(TokenValidityPeriodProvider.class, new ConfigBasedTokenValidityPeriodProvider());

    UserAppService userAppService = new UserAppServiceLoggingDecorator(new UserAppServiceImpl());
    register(UserAppService.class, userAppService);
    register(AccessController.class, userAppService);
    UserBoundedContextEventHandler userBoundedContextEventHandler =
        new UserBoundedContextEventHandler(userAppService);
    userBoundedContextEventHandler.register(mediator);
  }

  private PasswordManager createPasswordManager() {
    try {
      return new PbkdfPasswordManager(new InMemoryPbkdfPasswordStorage());
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  private void registerAdminUser(
      Properties properties, UsernameRegistry usernameRegistry, PasswordManager passwordManager) {
    String adminKey = String.valueOf(properties.getProperty("admin.key"));
    String adminUserName = String.valueOf(properties.getProperty("admin.username"));
    String adminPassword = String.valueOf(properties.getProperty("admin.password"));
    registerUser(usernameRegistry, passwordManager, adminKey, adminUserName, adminPassword);
  }

  private void registerActuaryUser(
      Properties properties, UsernameRegistry usernameRegistry, PasswordManager passwordManager) {
    String actuaryKey = String.valueOf(properties.getProperty("actuary.key"));
    String actuaryUserName = String.valueOf(properties.getProperty("actuary.username"));
    String actuaryPassword = String.valueOf(properties.getProperty("actuary.password"));
    registerUser(usernameRegistry, passwordManager, actuaryKey, actuaryUserName, actuaryPassword);
  }

  private void registerUser(
      UsernameRegistry usernameRegistry,
      PasswordManager passwordManager,
      String userKey,
      String userName,
      String password) {
    try {
      usernameRegistry.register(userKey, userName);
      passwordManager.registerPassword(userKey, password);
    } catch (KeyAlreadyExistException | InvalidPasswordException e) {
      e.printStackTrace();
    }
  }
}
