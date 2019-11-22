package ca.ulaval.glo4003.insuring.context;

import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.renewal.PolicyRenewalProcessor;
import ca.ulaval.glo4003.insuring.application.policy.renewal.TaskSchedulerPolicyRenewalProcessor;
import ca.ulaval.glo4003.insuring.communication.policy.PolicyBoundedContextEventHandler;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationValidityPeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyCoveragePeriodProvider;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalPeriodProvider;
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
    register(PolicyRenewalProcessor.class, new TaskSchedulerPolicyRenewalProcessor());
    register(PolicyCoveragePeriodProvider.class, new ConfigBasedPolicyCoveragePeriodProvider());
    register(
        PolicyRepository.class,
        new EventPublisherPolicyRepositoryDecorator(new InMemoryPolicyRepository(), mediator));
    register(ClaimRepository.class, new InMemoryClaimRepository());
    PolicyAppService policyAppService = new PolicyAppService();
    PolicyBoundedContextEventHandler policyBoundedContextEventHandler =
        new PolicyBoundedContextEventHandler(policyAppService);
    policyBoundedContextEventHandler.register(mediator);
  }
}
