package ca.ulaval.glo4003.coverage.infrastructure.form.validation;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.form.validationpart.UlRegistrarOffice;

public class DummyUlRegistrarOffice implements UlRegistrarOffice {
  @Override
  public boolean isValidRegistration(
      String idul, String identificationNumber, UniversityProgram program) {
    return true;
  }
}
