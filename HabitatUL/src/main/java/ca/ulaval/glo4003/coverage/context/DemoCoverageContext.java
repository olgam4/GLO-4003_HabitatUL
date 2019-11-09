package ca.ulaval.glo4003.coverage.context;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.communication.policy.PolicyBoundedContextEventHandler;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.coverage.persistence.claim.InMemoryClaimRepository;
import ca.ulaval.glo4003.coverage.persistence.policy.EventPublisherPolicyRepositoryDecorator;
import ca.ulaval.glo4003.coverage.persistence.policy.InMemoryPolicyRepository;
import ca.ulaval.glo4003.mediator.Mediator;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoCoverageContext {
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
