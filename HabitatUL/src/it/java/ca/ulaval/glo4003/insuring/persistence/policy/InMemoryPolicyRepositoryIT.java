package ca.ulaval.glo4003.insuring.persistence.policy;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepositoryIT;

public class InMemoryPolicyRepositoryIT extends PolicyRepositoryIT {
  @Override
  protected PolicyRepository createSubject() {
    return new InMemoryPolicyRepository();
  }
}
