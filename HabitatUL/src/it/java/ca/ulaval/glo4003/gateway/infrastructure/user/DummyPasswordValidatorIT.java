package ca.ulaval.glo4003.gateway.infrastructure.user;

import ca.ulaval.glo4003.gateway.domain.user.PasswordValidator;
import ca.ulaval.glo4003.gateway.domain.user.PasswordValidatorIT;

public class DummyPasswordValidatorIT extends PasswordValidatorIT {
  @Override
  public PasswordValidator createSubject() {
    return new DummyPasswordValidator();
  }
}
