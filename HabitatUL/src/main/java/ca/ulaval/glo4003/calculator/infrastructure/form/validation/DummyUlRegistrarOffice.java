package ca.ulaval.glo4003.calculator.infrastructure.form.validation;

import ca.ulaval.glo4003.calculator.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.calculator.domain.form.validation.UlRegistrarOffice;

public class DummyUlRegistrarOffice implements UlRegistrarOffice {
  @Override
  public boolean isValidRegistration(
      String idul, String identificationNumber, UniversityProgram program) {
    return true;
  }
}
