package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

public interface UlRegistrarOffice {
  boolean isValidRegistration(String idul, String identificationNumber, String program);
}
