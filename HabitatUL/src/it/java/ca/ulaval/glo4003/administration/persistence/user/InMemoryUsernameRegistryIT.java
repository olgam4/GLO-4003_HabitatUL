package ca.ulaval.glo4003.administration.persistence.user;

import ca.ulaval.glo4003.administration.domain.user.UsernameRegistry;
import ca.ulaval.glo4003.administration.domain.user.UsernameRegistryIT;

;

public class InMemoryUsernameRegistryIT extends UsernameRegistryIT {
  @Override
  protected UsernameRegistry createSubject() {
    return new InMemoryUsernameRegistry();
  }
}
