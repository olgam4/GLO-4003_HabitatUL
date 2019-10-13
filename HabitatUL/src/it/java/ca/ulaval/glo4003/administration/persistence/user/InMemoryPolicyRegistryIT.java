package ca.ulaval.glo4003.administration.persistence.user;

import ca.ulaval.glo4003.administration.domain.user.PolicyRegistry;
import ca.ulaval.glo4003.administration.domain.user.PolicyRegistryIT;

public class InMemoryPolicyRegistryIT extends PolicyRegistryIT {
  @Override
  public PolicyRegistry createSubject() {
    return new InMemoryPolicyRegistry();
  }
}
