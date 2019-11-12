package ca.ulaval.glo4003.coverage.domain.form.validation;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.DifferentAdditionalInsuredError;

public class DifferentAdditionalInsuredQuoteFormValidation implements QuoteFormValidation {
  @Override
  public void validate(QuoteForm quoteForm) {
    if (isNamedAndAdditionalInsuredSamePerson(quoteForm)) {
      throw new DifferentAdditionalInsuredError();
    }
  }

  private boolean isNamedAndAdditionalInsuredSamePerson(QuoteForm quoteForm) {
    return quoteForm.getPersonalInformation().equals(quoteForm.getAdditionalInsured());
  }
}
