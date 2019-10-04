package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.coverage.persistence.policy.EventPublisherPolicyRepositoryWrapper;
import ca.ulaval.glo4003.coverage.persistence.policy.InMemoryPolicyRepository;
import ca.ulaval.glo4003.coverage.presentation.policy.PolicyBoundedContext;
import ca.ulaval.glo4003.management.domain.user.User;
import ca.ulaval.glo4003.management.domain.user.UserId;
import ca.ulaval.glo4003.management.domain.user.UserRepository;
import ca.ulaval.glo4003.management.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.management.infrastructure.user.DummyPasswordValidator;
import ca.ulaval.glo4003.management.infrastructure.user.JwtTokenTranslator;
import ca.ulaval.glo4003.management.persistence.user.InMemoryUserRepository;
import ca.ulaval.glo4003.mediator.BoundedContextMediator;
import ca.ulaval.glo4003.mediator.ConcreteBoundedContextMediator;
import ca.ulaval.glo4003.mediator.event.EventChannel;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import ca.ulaval.glo4003.shared.infrastructure.SystemUtcClockProvider;
import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.DummyQuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.persistence.quote.EventPublisherQuoteRepositoryWrapper;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

import java.math.BigDecimal;
import java.util.Properties;

public class DemoContext implements Context {
  @Override
  public void execute() {
    BoundedContextMediator mediator = new ConcreteBoundedContextMediator();
    registerGeneralServices();
    registerManagementServices();
    registerUnderwritingServices(mediator);
    registerCoverageServices(mediator);
  }

  private void registerGeneralServices() {
    ServiceLocator.register(ClockProvider.class, new SystemUtcClockProvider());
  }

  private void registerManagementServices() {
    UserRepository userRepository = new InMemoryUserRepository();
    PasswordValidator passwordValidator = new DummyPasswordValidator();
    registerAdminUser(userRepository, passwordValidator);
    ServiceLocator.register(PasswordValidator.class, passwordValidator);
    ServiceLocator.register(UserRepository.class, userRepository);
    registerTokenTranslator();
  }

  private void registerAdminUser(
      UserRepository userRepository, PasswordValidator passwordValidator) {
    Properties properties = ConfigFileReader.readProperties("config.properties");
    String adminKey = String.valueOf(properties.getProperty("admin.key"));
    String adminName = String.valueOf(properties.getProperty("admin.username"));
    String adminPassword = String.valueOf(properties.getProperty("admin.password"));
    UserId adminId = new UserId(adminKey);
    User adminUser = new User(adminId, adminName);
    passwordValidator.registerPassword(adminName, adminPassword);
    userRepository.create(adminUser);
  }

  private void registerTokenTranslator() {
    Properties properties = ConfigFileReader.readProperties("config.properties");
    String jwtSecret = String.valueOf(properties.getProperty("jwt.secret"));
    ServiceLocator.register(TokenTranslator.class, new JwtTokenTranslator(jwtSecret));
  }

  private void registerUnderwritingServices(BoundedContextMediator mediator) {
    Premium hardCodedPremium = new Premium(BigDecimal.valueOf(534.32423423423));
    ServiceLocator.register(
        QuotePremiumCalculator.class, new DummyQuotePremiumCalculator(hardCodedPremium));
    ServiceLocator.register(
        QuoteValidityPeriodProvider.class, new ConfigBasedQuoteValidityPeriodProvider());
    ServiceLocator.register(
        QuoteRepository.class,
        new EventPublisherQuoteRepositoryWrapper(new InMemoryQuoteRepository(), mediator));
  }

  private void registerCoverageServices(BoundedContextMediator mediator) {
    ServiceLocator.register(
        PolicyRepository.class,
        new EventPublisherPolicyRepositoryWrapper(new InMemoryPolicyRepository(), mediator));
    PolicyAppService policyAppService = new PolicyAppService();
    PolicyBoundedContext policyBoundedContext = new PolicyBoundedContext(policyAppService);
    mediator.subscribe(policyBoundedContext, EventChannel.QUOTES);
  }
}
