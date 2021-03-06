package ca.ulaval.glo4003.insuring.context;

import ca.ulaval.glo4003.insuring.application.claim.ClaimAppService;
import ca.ulaval.glo4003.insuring.application.claim.ClaimAppServiceImpl;
import ca.ulaval.glo4003.insuring.application.claim.ClaimAppServiceLoggingDecorator;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppServiceConcurrentDecorator;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppServiceImpl;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppServiceLoggingDecorator;
import ca.ulaval.glo4003.insuring.application.policy.claimexpiration.ClaimExpirationPeriodProvider;
import ca.ulaval.glo4003.insuring.application.policy.claimexpiration.ClaimExpirationProcessor;
import ca.ulaval.glo4003.insuring.application.policy.claimexpiration.ClaimExpirationProcessorLoggingDecorator;
import ca.ulaval.glo4003.insuring.application.policy.claimexpiration.TaskSchedulerClaimExpirationProcessor;
import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioConfigurer;
import ca.ulaval.glo4003.insuring.application.policy.lossratio.MaximumLossRatioProvider;
import ca.ulaval.glo4003.insuring.application.policy.renewal.PolicyRenewalProcessor;
import ca.ulaval.glo4003.insuring.application.policy.renewal.PolicyRenewalProcessorLoggingDecorator;
import ca.ulaval.glo4003.insuring.application.policy.renewal.TaskSchedulerPolicyRenewalProcessor;
import ca.ulaval.glo4003.insuring.communication.policy.PolicyBoundedContextEventHandler;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalPeriodProvider;
import ca.ulaval.glo4003.insuring.infrastructure.policy.claimexpiration.ConfigBasedClaimExpirationPeriodProvider;
import ca.ulaval.glo4003.insuring.infrastructure.policy.lossratio.InMemoryMaximumLossRatioConfiguration;
import ca.ulaval.glo4003.insuring.infrastructure.policy.modification.ConfigBasedPolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.infrastructure.policy.renewal.ConfigBasedPolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.insuring.infrastructure.policy.renewal.ConfigBasedPolicyRenewalPeriodProvider;
import ca.ulaval.glo4003.insuring.persistence.claim.InMemoryClaimRepository;
import ca.ulaval.glo4003.insuring.persistence.policy.EventPublisherPolicyRepositoryDecorator;
import ca.ulaval.glo4003.insuring.persistence.policy.InMemoryPolicyRepository;
import ca.ulaval.glo4003.mediator.Mediator;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoInsuringContext {
  public void execute(Mediator mediator) {
    register(
        PolicyModificationValidityPeriodProvider.class,
        new ConfigBasedPolicyModificationValidityPeriodProvider());
    register(PolicyRenewalPeriodProvider.class, new ConfigBasedPolicyRenewalPeriodProvider());
    register(PolicyCoveragePeriodProvider.class, new ConfigBasedPolicyCoveragePeriodProvider());
    register(ClaimExpirationPeriodProvider.class, new ConfigBasedClaimExpirationPeriodProvider());
    InMemoryMaximumLossRatioConfiguration maximumLossRatioConfiguration =
        new InMemoryMaximumLossRatioConfiguration();
    register(MaximumLossRatioProvider.class, maximumLossRatioConfiguration);
    register(MaximumLossRatioConfigurer.class, maximumLossRatioConfiguration);
    register(
        PolicyRepository.class,
        new EventPublisherPolicyRepositoryDecorator(new InMemoryPolicyRepository(), mediator));
    register(ClaimRepository.class, new InMemoryClaimRepository());
    register(
        PolicyRenewalProcessor.class,
        new PolicyRenewalProcessorLoggingDecorator(new TaskSchedulerPolicyRenewalProcessor()));
    register(
        ClaimExpirationProcessor.class,
        new ClaimExpirationProcessorLoggingDecorator(new TaskSchedulerClaimExpirationProcessor()));
    PolicyAppService policyAppService =
        new PolicyAppServiceLoggingDecorator(
            new PolicyAppServiceConcurrentDecorator(new PolicyAppServiceImpl()));
    ClaimAppService claimAppService =
        new ClaimAppServiceLoggingDecorator(new ClaimAppServiceImpl());
    PolicyBoundedContextEventHandler policyBoundedContextEventHandler =
        new PolicyBoundedContextEventHandler(policyAppService);
    register(PolicyAppService.class, policyAppService);
    register(ClaimAppService.class, claimAppService);
    policyBoundedContextEventHandler.register(mediator);
  }
}
