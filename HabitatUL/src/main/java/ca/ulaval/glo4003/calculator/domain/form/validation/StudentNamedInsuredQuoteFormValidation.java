package ca.ulaval.glo4003.calculator.domain.form.validation;

import ca.ulaval.glo4003.calculator.domain.form.QuoteForm;
import ca.ulaval.glo4003.calculator.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteStudentNamedInsuredError;

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
