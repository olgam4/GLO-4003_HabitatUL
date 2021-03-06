package ca.ulaval.glo4003.insuring.persistence.policy;

import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.mediator.Mediator;

import java.util.List;

public class EventPublisherPolicyRepositoryDecorator implements PolicyRepository {
  private PolicyRepository policyRepository;
  private Mediator mediator;

  public EventPublisherPolicyRepositoryDecorator(
      PolicyRepository policyRepository, Mediator mediator) {
    this.policyRepository = policyRepository;
    this.mediator = mediator;
  }

  @Override
  public List<Policy> getAll() {
    return policyRepository.getAll();
  }

  @Override
  public Policy getById(PolicyId policyId) throws PolicyNotFoundException {
    return policyRepository.getById(policyId);
  }

  @Override
  public void create(Policy policy) throws PolicyAlreadyCreatedException {
    policyRepository.create(policy);
    publishEvents(policy);
  }

  @Override
  public void update(Policy policy) throws PolicyNotFoundException {
    policyRepository.update(policy);
    publishEvents(policy);
  }

  private void publishEvents(Policy policy) {
    mediator.publish(policy.getEvents());
  }
}
