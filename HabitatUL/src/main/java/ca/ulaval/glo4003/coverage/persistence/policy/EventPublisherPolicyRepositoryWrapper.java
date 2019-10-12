package ca.ulaval.glo4003.coverage.persistence.policy;

import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.mediator.Mediator;

public class EventPublisherPolicyRepositoryWrapper implements PolicyRepository {
  private PolicyRepository policyRepository;
  private Mediator mediator;

  public EventPublisherPolicyRepositoryWrapper(
      PolicyRepository policyRepository, Mediator mediator) {
    this.policyRepository = policyRepository;
    this.mediator = mediator;
  }

  @Override
  public void create(Policy policy) {
    policyRepository.create(policy);
    publishEvents(policy);
  }

  @Override
  public Policy getById(PolicyId policyId) {
    return policyRepository.getById(policyId);
  }

  private void publishEvents(Policy policy) {
    mediator.publish(policy.getEvents());
  }
}
