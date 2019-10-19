package ca.ulaval.glo4003.underwriting.infrastructure.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.form.validation.UlRegistrarOffice;
import ca.ulaval.glo4003.underwriting.domain.quote.form.validation.UlRegistrarOfficeIT;

public class DummyUlRegistrarOfficeIT extends UlRegistrarOfficeIT {
  @Override
  public UlRegistrarOffice createSubject() {
    return new DummyUlRegistrarOffice();
  }
}
