package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.StudentNamedInsuredError;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;

public class StudentNamedInsuredFormValidationPart implements QuoteFormValidationPart {
  @Override
  public void validate(QuoteForm form) {
    UniversityProfile namedInsuredUniversityProfile =
        form.getPersonalInformation().getUniversityProfile();
    if (!namedInsuredUniversityProfile.isFilled()) {
      throw new StudentNamedInsuredError();
    }
  }
}
