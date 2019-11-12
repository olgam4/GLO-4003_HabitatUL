package ca.ulaval.glo4003.calculator.infrastructure.form.validation;

import ca.ulaval.glo4003.calculator.domain.form.validation.UlRegistrarOffice;
import ca.ulaval.glo4003.calculator.domain.form.validation.UlRegistrarOfficeIT;

public class DummyUlRegistrarOfficeIT extends UlRegistrarOfficeIT {
  @Override
  public UlRegistrarOffice createSubject() {
    return new DummyUlRegistrarOffice();
  }
}
