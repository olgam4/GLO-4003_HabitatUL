package ca.ulaval.glo4003.insuring.context;

import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.communication.policy.PolicyBoundedContextEventHandler;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.persistence.claim.InMemoryClaimRepository;
import ca.ulaval.glo4003.insuring.persistence.policy.EventPublisherPolicyRepositoryDecorator;
import ca.ulaval.glo4003.insuring.persistence.policy.InMemoryPolicyRepository;
import ca.ulaval.glo4003.mediator.Mediator;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoInsuringContext {
  public void execute(Mediator mediator) {
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
