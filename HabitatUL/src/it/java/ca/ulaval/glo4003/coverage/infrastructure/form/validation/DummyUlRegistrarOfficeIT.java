package ca.ulaval.glo4003.coverage.infrastructure.form.validation;

import ca.ulaval.glo4003.coverage.domain.form.validationpart.UlRegistrarOffice;
import ca.ulaval.glo4003.coverage.domain.form.validationpart.UlRegistrarOfficeIT;

public class DummyUlRegistrarOfficeIT extends UlRegistrarOfficeIT {
  @Override
  public UlRegistrarOffice createSubject() {
    return new DummyUlRegistrarOffice();
  }
}
