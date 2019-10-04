package ca.ulaval.glo4003.management.infrastructure.user;

import ca.ulaval.glo4003.management.domain.user.PolicyRegistry;
import ca.ulaval.glo4003.management.domain.user.PolicyRegistryIT;
import ca.ulaval.glo4003.management.persistence.user.InMemoryPolicyRegistry;

public class InMemoryPolicyRegistryIT extends PolicyRegistryIT {
  @Override
  public PolicyRegistry createSubject() {
    return new InMemoryPolicyRegistry();
  }
}
