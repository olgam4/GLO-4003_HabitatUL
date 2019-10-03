package ca.ulaval.glo4003.gateway.infrastructure.user;

import ca.ulaval.glo4003.gateway.domain.user.PasswordValidatorIT;
import ca.ulaval.glo4003.gateway.domain.user.credential.PasswordValidator;

public class DummyPasswordValidatorIT extends PasswordValidatorIT {
  @Override
  public PasswordValidator createSubject() {
    return new DummyPasswordValidator();
  }
}
