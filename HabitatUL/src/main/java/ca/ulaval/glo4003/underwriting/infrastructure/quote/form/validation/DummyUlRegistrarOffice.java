package ca.ulaval.glo4003.underwriting.infrastructure.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.form.validation.UlRegistrarOffice;

public class DummyUlRegistrarOffice implements UlRegistrarOffice {
  @Override
  public boolean isValidRegistration(String idul, String identificationNumber, String program) {
    return true;
  }
}
