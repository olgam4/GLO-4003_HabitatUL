package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.calculator.domain.input.UniversityProgram;

public interface UlRegistrarOffice {
  boolean isValidRegistration(String idul, String identificationNumber, UniversityProgram program);
}
