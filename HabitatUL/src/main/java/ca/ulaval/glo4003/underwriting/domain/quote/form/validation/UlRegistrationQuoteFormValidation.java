package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.calculator.domain.premium.formula.input.UniversityProgram;
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
    validateNamedInsuredUniversityProfile(quoteForm);
    validateAdditionalInsuredUniversityProfile(quoteForm);
  }

  private void validateNamedInsuredUniversityProfile(QuoteForm quoteForm) {
    UniversityProfile namedInsuredUniversityProfile =
        quoteForm.getPersonalInformation().getUniversityProfile();
    validateUniversityProfile(namedInsuredUniversityProfile);
  }

  private void validateAdditionalInsuredUniversityProfile(QuoteForm quoteForm) {
    UniversityProfile additionalInsuredUniversityProfile =
        quoteForm.getAdditionalInsured().getUniversityProfile();
    validateUniversityProfile(additionalInsuredUniversityProfile);
  }

  private void validateUniversityProfile(UniversityProfile universityProfile) {
    if (universityProfile.isFilled()) {
      String idul = universityProfile.getIdul();
      String identificationNumber = universityProfile.getIdentificationNumber();
      UniversityProgram program = universityProfile.getProgram();
      if (!ulRegistrarOffice.isValidRegistration(idul, identificationNumber, program)) {
        throw new QuoteUniversityProfileError();
      }
    }
  }
}
