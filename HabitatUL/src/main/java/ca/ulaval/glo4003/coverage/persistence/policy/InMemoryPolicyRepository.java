package ca.ulaval.glo4003.coverage.persistence.policy;

import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.coverage.domain.policy.exception.PolicyAlreadyPersistedException;
import ca.ulaval.glo4003.coverage.domain.policy.exception.PolicyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPolicyRepository implements PolicyRepository {
  private Map<PolicyId, Policy> policies = new HashMap<>();

  @Override
  public void create(Policy policy) {
    PolicyId policyId = policy.getPolicyId();

    if (isExistingPolicy(policyId)) throw new PolicyAlreadyPersistedException(policyId);

    policies.put(policy.getPolicyId(), policy);
  }

  @Override
  public Policy getById(PolicyId policyId) {
    if (isExistingPolicy(policyId)) return policies.get(policyId);

    throw new PolicyNotFoundException(policyId);
  }

  private boolean isExistingPolicy(PolicyId policyId) {
    return policies.containsKey(policyId);
  }
}
