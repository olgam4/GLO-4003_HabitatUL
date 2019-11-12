package ca.ulaval.glo4003.coverage.domain.form.validationpart;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validationpart.error.StudentNamedInsuredError;

public class StudentNamedInsuredFormValidationPart implements QuoteFormValidationPart {
  @Override
  public void validate(QuoteForm quoteForm) {
    UniversityProfile namedInsuredUniversityProfile =
        quoteForm.getPersonalInformation().getUniversityProfile();
    if (!namedInsuredUniversityProfile.isFilled()) {
      throw new StudentNamedInsuredError();
    }
  }
}
