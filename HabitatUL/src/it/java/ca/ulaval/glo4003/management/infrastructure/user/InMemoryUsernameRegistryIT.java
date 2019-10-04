package ca.ulaval.glo4003.management.infrastructure.user;

import ca.ulaval.glo4003.management.domain.user.UsernameRegistry;
import ca.ulaval.glo4003.management.domain.user.UsernameRegistryIT;
import ca.ulaval.glo4003.management.persistence.user.InMemoryUsernameRegistry;

;

public class InMemoryUsernameRegistryIT extends UsernameRegistryIT {
  @Override
  protected UsernameRegistry createSubject() {
    return new InMemoryUsernameRegistry();
  }
}
