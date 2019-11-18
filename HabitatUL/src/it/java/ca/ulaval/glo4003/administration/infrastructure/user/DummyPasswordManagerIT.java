package ca.ulaval.glo4003.administration.infrastructure.user;

import ca.ulaval.glo4003.administration.domain.user.PasswordManagerIT;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManager;

public class DummyPasswordManagerIT extends PasswordManagerIT {
  @Override
  public PasswordManager createSubject() {
    return new DummyPasswordManager();
  }
}
