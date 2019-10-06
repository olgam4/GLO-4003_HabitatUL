package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.TokenRegistry;
import ca.ulaval.glo4003.management.domain.user.TokenRegistryIT;

public class InMemoryTokenRegistryIT extends TokenRegistryIT {
  @Override
  protected TokenRegistry createSubject() {
    return new InMemoryTokenRegistry();
  }
}
