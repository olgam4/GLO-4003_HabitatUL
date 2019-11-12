package ca.ulaval.glo4003.coverage.domain.form.validationpart;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;

public interface UlRegistrarOffice {
  boolean isValidRegistration(String idul, String identificationNumber, UniversityProgram program);
}
