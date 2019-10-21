package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteUniversityProfileError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;

public class UlRegistrationQuoteFormValidation implements QuoteFormValidation {
  private UlRegistrarOffice ulRegistrarOffice;

  public UlRegistrationQuoteFormValidation(UlRegistrarOffice ulRegistrarOffice) {
    this.ulRegistrarOffice = ulRegistrarOffice;
  }

  @Override
  public void validate(QuoteForm quoteForm) {
    UniversityProfile universityProfile = quoteForm.getPersonalInformation().getUniversityProfile();
    String idul = universityProfile.getIdul();
    String identificationNumber = universityProfile.getIdentificationNumber();
    String program = universityProfile.getProgram();
    if (!ulRegistrarOffice.isValidRegistration(idul, identificationNumber, program)) {
      throw new QuoteUniversityProfileError();
    }
  }
}
