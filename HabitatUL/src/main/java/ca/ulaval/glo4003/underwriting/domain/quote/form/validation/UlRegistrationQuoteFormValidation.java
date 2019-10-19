package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteStudentInformationError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.studentinformation.StudentInformation;

public class UlRegistrationQuoteFormValidation implements QuoteFormValidation {
  private UlRegistrarOffice ulRegistrarOffice;

  public UlRegistrationQuoteFormValidation(UlRegistrarOffice ulRegistrarOffice) {
    this.ulRegistrarOffice = ulRegistrarOffice;
  }

  @Override
  public void validate(QuoteForm quoteForm) {
    StudentInformation studentInformation = quoteForm.getStudentInformation();
    String idul = studentInformation.getIdul();
    String identificationNumber = studentInformation.getIdentificationNumber();
    String program = studentInformation.getProgram();
    if (!ulRegistrarOffice.isValidRegistration(idul, identificationNumber, program)) {
      throw new QuoteStudentInformationError();
    }
  }
}
