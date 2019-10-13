package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.communication.policy.PolicyBoundedContextEventHandler;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.coverage.persistence.claim.InMemoryClaimRepository;
import ca.ulaval.glo4003.coverage.persistence.policy.EventPublisherPolicyRepositoryWrapper;
import ca.ulaval.glo4003.coverage.persistence.policy.InMemoryPolicyRepository;
import ca.ulaval.glo4003.gateway.presentation.common.databind.ConfigBasedLocalZoneIdProvider;
import ca.ulaval.glo4003.gateway.presentation.common.databind.LocalZoneIdProvider;
import ca.ulaval.glo4003.management.application.user.AccessController;
import ca.ulaval.glo4003.management.application.user.UserAppService;
import ca.ulaval.glo4003.management.communication.user.UserBoundedContextEventHandler;
import ca.ulaval.glo4003.management.domain.user.*;
import ca.ulaval.glo4003.management.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.management.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.management.infrastructure.user.AlwaysOkPaymentProcessor;
import ca.ulaval.glo4003.management.infrastructure.user.ConfigBasedTokenValidityPeriodProvider;
import ca.ulaval.glo4003.management.infrastructure.user.DummyPasswordValidator;
import ca.ulaval.glo4003.management.infrastructure.user.JwtTokenTranslator;
import ca.ulaval.glo4003.management.persistence.user.InMemoryPolicyRegistry;
import ca.ulaval.glo4003.management.persistence.user.InMemoryQuoteRegistry;
import ca.ulaval.glo4003.management.persistence.user.InMemoryTokenRegistry;
import ca.ulaval.glo4003.management.persistence.user.InMemoryUsernameRegistry;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import ca.ulaval.glo4003.shared.infrastructure.SystemUtcClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteIndicatedPriceCalculator;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.price.DummyQuoteIndicatedPriceCalculator;
import ca.ulaval.glo4003.underwriting.persistence.quote.EventPublisherQuoteRepositoryWrapper;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

import java.math.BigDecimal;
import java.util.Properties;

public class DemoContext implements Context {
  private Properties properties;
  private Mediator mediator;

  public DemoContext() {
    ServiceLocator.reset();
    properties = ConfigFileReader.readProperties("config.properties");
    mediator = new Mediator();
  }

  @Override
  public void execute() {
    registerCommonServices();
    registerManagementServices();
    registerUnderwritingServices();
    registerCoverageServices();
  }

  private void registerCommonServices() {
    MediatorChanneler.registerChannels(mediator);
    ServiceLocator.register(ClockProvider.class, new SystemUtcClockProvider());
    ServiceLocator.register(LocalZoneIdProvider.class, new ConfigBasedLocalZoneIdProvider());
  }

  private void registerManagementServices() {
    InMemoryUsernameRegistry usernameRegistry = new InMemoryUsernameRegistry();
    PasswordValidator passwordValidator = new DummyPasswordValidator();
    registerAdminUser(properties, usernameRegistry, passwordValidator);

    ServiceLocator.register(PasswordValidator.class, passwordValidator);
    ServiceLocator.register(PaymentProcessor.class, new AlwaysOkPaymentProcessor());
    ServiceLocator.register(PolicyRegistry.class, new InMemoryPolicyRegistry());
    ServiceLocator.register(QuoteRegistry.class, new InMemoryQuoteRegistry());
    ServiceLocator.register(UsernameRegistry.class, usernameRegistry);
    ServiceLocator.register(TokenRegistry.class, new InMemoryTokenRegistry());
    String jwtSecret = String.valueOf(properties.getProperty("jwt.secret"));
    ServiceLocator.register(TokenTranslator.class, new JwtTokenTranslator(jwtSecret));
    ServiceLocator.register(
        TokenValidityPeriodProvider.class, new ConfigBasedTokenValidityPeriodProvider());

    UserAppService userAppService = new UserAppService();
    ServiceLocator.register(AccessController.class, userAppService);
    UserBoundedContextEventHandler userBoundedContextEventHandler =
        new UserBoundedContextEventHandler(userAppService);
    userBoundedContextEventHandler.register(mediator);
  }

  private void registerAdminUser(
      Properties properties,
      UsernameRegistry usernameRegistry,
      PasswordValidator passwordValidator) {
    String adminKey = String.valueOf(properties.getProperty("admin.key"));
    String adminName = String.valueOf(properties.getProperty("admin.username"));
    String adminPassword = String.valueOf(properties.getProperty("admin.password"));
    usernameRegistry.register(adminKey, adminName);
    passwordValidator.registerPassword(adminName, adminPassword);
  }

  private void registerUnderwritingServices() {
    Money hardCodedPrice = new Money(BigDecimal.valueOf(200));
    ServiceLocator.register(
        QuoteIndicatedPriceCalculator.class,
        new DummyQuoteIndicatedPriceCalculator(hardCodedPrice));
    ServiceLocator.register(
        QuoteValidityPeriodProvider.class, new ConfigBasedQuoteValidityPeriodProvider());
    ServiceLocator.register(
        QuoteRepository.class,
        new EventPublisherQuoteRepositoryWrapper(new InMemoryQuoteRepository(), mediator));
  }

  private void registerCoverageServices() {
    ServiceLocator.register(
        PolicyRepository.class,
        new EventPublisherPolicyRepositoryWrapper(new InMemoryPolicyRepository(), mediator));
    ServiceLocator.register(ClaimRepository.class, new InMemoryClaimRepository());

    PolicyAppService policyAppService = new PolicyAppService();
    PolicyBoundedContextEventHandler policyBoundedContextEventHandler =
        new PolicyBoundedContextEventHandler(policyAppService);
    policyBoundedContextEventHandler.register(mediator);
  }
}
