package ca.ulaval.glo4003.management.infrastructure.user;

import ca.ulaval.glo4003.management.domain.user.PasswordValidatorIT;
import ca.ulaval.glo4003.management.domain.user.credential.PasswordValidator;

public class DummyPasswordValidatorIT extends PasswordValidatorIT {
  @Override
  public PasswordValidator createSubject() {
    return new DummyPasswordValidator();
  }
}
