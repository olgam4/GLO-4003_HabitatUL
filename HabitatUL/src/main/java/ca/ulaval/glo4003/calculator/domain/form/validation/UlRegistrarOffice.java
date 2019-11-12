package ca.ulaval.glo4003.calculator.domain.form.validation;

import ca.ulaval.glo4003.calculator.domain.form.identity.UniversityProgram;

public interface UlRegistrarOffice {
  boolean isValidRegistration(String idul, String identificationNumber, UniversityProgram program);
}
