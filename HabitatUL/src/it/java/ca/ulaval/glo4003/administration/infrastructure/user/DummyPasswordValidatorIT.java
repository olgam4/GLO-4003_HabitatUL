package ca.ulaval.glo4003.administration.infrastructure.user;

import ca.ulaval.glo4003.administration.domain.user.PasswordValidatorIT;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordValidator;

public class DummyPasswordValidatorIT extends PasswordValidatorIT {
  @Override
  public PasswordValidator createSubject() {
    return new DummyPasswordValidator();
  }
}
