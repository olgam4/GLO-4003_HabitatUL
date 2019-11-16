package ca.ulaval.glo4003.coverage.infrastructure.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.validation.part.UlRegistrarOffice;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.UlRegistrarOfficeIT;
import ca.ulaval.glo4003.coverage.infrastructure.form.validation.DummyUlRegistrarOffice;

public class DummyUlRegistrarOfficeIT extends UlRegistrarOfficeIT {
  @Override
  public UlRegistrarOffice createSubject() {
    return new DummyUlRegistrarOffice();
  }
}
