package ca.ulaval.glo4003.coverage.persistence.policy;

import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyHolderId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;

import java.util.List;

public class InMemoryPolicyRepository implements PolicyRepository {
  @Override
  public List<Policy> getByPolicyHolderId(PolicyHolderId policyHolderId) {
    return null;
  }
}
