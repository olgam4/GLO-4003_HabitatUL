package ca.ulaval.glo4003.administration.persistence.user;

import ca.ulaval.glo4003.administration.domain.user.TokenRegistry;
import ca.ulaval.glo4003.administration.domain.user.TokenRegistryIT;

public class InMemoryTokenRegistryIT extends TokenRegistryIT {
  @Override
  protected TokenRegistry createSubject() {
    return new InMemoryTokenRegistry();
  }
}
