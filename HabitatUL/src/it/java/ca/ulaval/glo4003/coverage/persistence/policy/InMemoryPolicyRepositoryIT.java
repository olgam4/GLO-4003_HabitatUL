package ca.ulaval.glo4003.coverage.persistence.policy;

import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepositoryIT;

public class InMemoryPolicyRepositoryIT extends PolicyRepositoryIT {
  @Override
  protected PolicyRepository createSubject() {
    return new InMemoryPolicyRepository();
  }
}
