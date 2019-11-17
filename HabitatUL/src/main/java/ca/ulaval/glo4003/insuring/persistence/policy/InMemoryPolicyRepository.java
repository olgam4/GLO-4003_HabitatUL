package ca.ulaval.glo4003.insuring.persistence.policy;

import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPolicyRepository implements PolicyRepository {
  private Map<PolicyId, Policy> policies = new HashMap<>();

  @Override
  public Policy getById(PolicyId policyId) throws PolicyNotFoundException {
    if (isExistingPolicy(policyId)) return policies.get(policyId);

    throw new PolicyNotFoundException();
  }

  @Override
  public void create(Policy policy) throws PolicyAlreadyCreatedException {
    PolicyId policyId = policy.getPolicyId();

    if (isExistingPolicy(policyId)) throw new PolicyAlreadyCreatedException();

    policies.put(policy.getPolicyId(), policy);
  }

  @Override
  public void update(Policy policy) throws PolicyNotFoundException {
    PolicyId policyId = policy.getPolicyId();

    if (!isExistingPolicy(policyId)) throw new PolicyNotFoundException();

    policies.put(policyId, policy);
  }

  private boolean isExistingPolicy(PolicyId policyId) {
    return policies.containsKey(policyId);
  }
}
