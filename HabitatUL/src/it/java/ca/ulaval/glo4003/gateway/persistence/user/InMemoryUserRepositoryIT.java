package ca.ulaval.glo4003.gateway.persistence.user;

import ca.ulaval.glo4003.gateway.domain.user.UserRepository;
import ca.ulaval.glo4003.gateway.domain.user.UserRepositoryIT;

public class InMemoryUserRepositoryIT extends UserRepositoryIT {
  @Override
  protected UserRepository createSubject() {
    return new InMemoryUserRepository();
  }
}
