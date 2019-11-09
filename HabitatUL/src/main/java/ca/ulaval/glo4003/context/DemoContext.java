package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.administration.application.user.AccessController;
import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.communication.user.UserBoundedContextEventHandler;
import ca.ulaval.glo4003.administration.domain.user.*;
import ca.ulaval.glo4003.administration.domain.user.credential.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordValidator;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.administration.domain.user.token.TokenValidityPeriodProvider;
import ca.ulaval.glo4003.administration.infrastructure.user.ConfigBasedTokenValidityPeriodProvider;
import ca.ulaval.glo4003.administration.infrastructure.user.DummyPasswordValidator;
import ca.ulaval.glo4003.administration.infrastructure.user.DummyPaymentProcessor;
import ca.ulaval.glo4003.administration.infrastructure.user.JwtTokenTranslator;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryPolicyRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryQuoteRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryTokenRegistry;
import ca.ulaval.glo4003.administration.persistence.user.InMemoryUsernameRegistry;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formula.quote.HardCodedQuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals.HardCodedAnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals.HardCodedAnimalsAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.civilliabilitylimit.HardCodedCivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.graduatestudent.HardCodedGraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.preferentialprogram.JsonPreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.roommate.HardCodedRoommateAdjustmentProvider;
import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.communication.policy.PolicyBoundedContextEventHandler;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.coverage.persistence.claim.InMemoryClaimRepository;
import ca.ulaval.glo4003.coverage.persistence.policy.EventPublisherPolicyRepositoryDecorator;
import ca.ulaval.glo4003.coverage.persistence.policy.InMemoryPolicyRepository;
import ca.ulaval.glo4003.gateway.presentation.common.databind.ConfigBasedLocalZoneIdProvider;
import ca.ulaval.glo4003.gateway.presentation.common.databind.LocalZoneIdProvider;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.ConfigFileReader;
import ca.ulaval.glo4003.shared.infrastructure.SystemDefaultZoneClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.FloorFormatter;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCodeFormatter;
import ca.ulaval.glo4003.underwriting.domain.quote.form.validation.UlRegistrarOffice;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteEffectivePeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.ConfigBasedQuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location.CanadianZipCodeFormatter;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location.UsCanadianConventionFloorFormatter;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.validation.DummyUlRegistrarOffice;
import ca.ulaval.glo4003.underwriting.persistence.quote.EventPublisherQuoteRepositoryDecorator;
import ca.ulaval.glo4003.underwriting.persistence.quote.InMemoryQuoteRepository;

import java.util.Properties;

public class DemoContext implements Context {
  private Properties properties;
  private Mediator mediator;

  public DemoContext() {
    ServiceLocator.reset();
    properties = new ConfigFileReader().read("config.properties");
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
    ServiceLocator.register(ClockProvider.class, new SystemDefaultZoneClockProvider());
    ServiceLocator.register(FloorFormatter.class, new UsCanadianConventionFloorFormatter());
    ServiceLocator.register(LocalZoneIdProvider.class, new ConfigBasedLocalZoneIdProvider());
    ServiceLocator.register(ZipCodeFormatter.class, new CanadianZipCodeFormatter());
  }

  private void registerManagementServices() {
    InMemoryUsernameRegistry usernameRegistry = new InMemoryUsernameRegistry();
    PasswordValidator passwordValidator = new DummyPasswordValidator();
    registerAdminUser(properties, usernameRegistry, passwordValidator);

    ServiceLocator.register(PasswordValidator.class, passwordValidator);
    ServiceLocator.register(PaymentProcessor.class, new DummyPaymentProcessor());
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
    try {
      usernameRegistry.register(adminKey, adminName);
      passwordValidator.registerPassword(adminName, adminPassword);
    } catch (KeyAlreadyExistException | InvalidPasswordException e) {
      e.printStackTrace();
    }
  }

  private void registerUnderwritingServices() {
    ServiceLocator.register(
        QuoteEffectivePeriodProvider.class, new ConfigBasedQuoteEffectivePeriodProvider());
    ServiceLocator.register(
        QuoteValidityPeriodProvider.class, new ConfigBasedQuoteValidityPeriodProvider());
    ServiceLocator.register(UlRegistrarOffice.class, new DummyUlRegistrarOffice());
    ServiceLocator.register(
        QuoteBasePremiumCalculator.class, new HardCodedQuoteBasePremiumCalculator());
    ServiceLocator.register(
        CivilLiabilityLimitAdjustmentProvider.class,
        new HardCodedCivilLiabilityLimitAdjustmentProvider());
    ServiceLocator.register(
        AnimalsAdjustmentProvider.class, new HardCodedAnimalsAdjustmentProvider());
    ServiceLocator.register(
        AnimalsAdjustmentLimitsProvider.class, new HardCodedAnimalsAdjustmentLimitsProvider());
    ServiceLocator.register(
        PreferentialProgramAdjustmentProvider.class,
        new JsonPreferentialProgramAdjustmentProvider());
    ServiceLocator.register(
        RoommateAdjustmentProvider.class, new HardCodedRoommateAdjustmentProvider());
    ServiceLocator.register(
        GraduateStudentAdjustmentProvider.class, new HardCodedGraduateStudentAdjustmentProvider());
    ServiceLocator.register(
        QuoteRepository.class,
        new EventPublisherQuoteRepositoryDecorator(new InMemoryQuoteRepository(), mediator));
  }

  private void registerCoverageServices() {
    ServiceLocator.register(
        PolicyRepository.class,
        new EventPublisherPolicyRepositoryDecorator(new InMemoryPolicyRepository(), mediator));
    ServiceLocator.register(ClaimRepository.class, new InMemoryClaimRepository());

    PolicyAppService policyAppService = new PolicyAppService();
    PolicyBoundedContextEventHandler policyBoundedContextEventHandler =
        new PolicyBoundedContextEventHandler(policyAppService);
    policyBoundedContextEventHandler.register(mediator);
  }
}
