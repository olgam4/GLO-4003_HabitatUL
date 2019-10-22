package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteDifferentAdditionalInsuredError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

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
