package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.UsernameRegistry;
import ca.ulaval.glo4003.management.domain.user.UsernameRegistryIT;

;

public class InMemoryUsernameRegistryIT extends UsernameRegistryIT {
  @Override
  protected UsernameRegistry createSubject() {
    return new InMemoryUsernameRegistry();
  }
}
