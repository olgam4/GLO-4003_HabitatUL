package ca.ulaval.glo4003.coverage.domain.form.validation;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.StudentNamedInsuredError;

public class StudentNamedInsuredQuoteFormValidation implements QuoteFormValidation {
  @Override
  public void validate(QuoteForm quoteForm) {
    UniversityProfile namedInsuredUniversityProfile =
        quoteForm.getPersonalInformation().getUniversityProfile();
    if (!namedInsuredUniversityProfile.isFilled()) {
      throw new StudentNamedInsuredError();
    }
  }
}
