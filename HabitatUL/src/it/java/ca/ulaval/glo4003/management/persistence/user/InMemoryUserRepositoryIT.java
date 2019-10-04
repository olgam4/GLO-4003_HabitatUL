package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.UserRepository;
import ca.ulaval.glo4003.management.domain.user.UserRepositoryIT;

public class InMemoryUserRepositoryIT extends UserRepositoryIT {
  @Override
  protected UserRepository createSubject() {
    return new InMemoryUserRepository();
  }
}
