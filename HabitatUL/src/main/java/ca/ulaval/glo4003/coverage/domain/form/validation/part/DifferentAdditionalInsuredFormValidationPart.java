package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.DifferentAdditionalInsuredError;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;

public class DifferentAdditionalInsuredFormValidationPart implements QuoteFormValidationPart {
  @Override
  public void validate(QuoteForm form) {
    if (isNamedAndAdditionalInsuredSamePerson(form)) {
      throw new DifferentAdditionalInsuredError();
    }
  }

  private boolean isNamedAndAdditionalInsuredSamePerson(QuoteForm form) {
    return form.getPersonalInformation().equals(form.getAdditionalInsured());
  }
}
