package ca.ulaval.glo4003.calculator.domain.form.validation;

import ca.ulaval.glo4003.calculator.domain.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteDifferentAdditionalInsuredError;

public class DifferentAdditionalInsuredQuoteFormValidation implements QuoteFormValidation {
  @Override
  public void validate(QuoteForm quoteForm) {
    if (isNamedAndAdditionalInsuredSamePerson(quoteForm)) {
      throw new QuoteDifferentAdditionalInsuredError();
    }
  }

  private boolean isNamedAndAdditionalInsuredSamePerson(QuoteForm quoteForm) {
    return quoteForm.getPersonalInformation().equals(quoteForm.getAdditionalInsured());
  }
}
