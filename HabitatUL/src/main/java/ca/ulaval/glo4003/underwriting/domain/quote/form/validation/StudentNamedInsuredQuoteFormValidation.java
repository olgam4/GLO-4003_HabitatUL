package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteStudentNamedInsuredError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;

public class StudentNamedInsuredQuoteFormValidation implements QuoteFormValidation {
  @Override
  public void validate(QuoteForm quoteForm) {
    UniversityProfile namedInsuredUniversityProfile =
        quoteForm.getPersonalInformation().getUniversityProfile();
    if (!namedInsuredUniversityProfile.isFilled()) {
      throw new QuoteStudentNamedInsuredError();
    }
  }
}
