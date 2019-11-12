package ca.ulaval.glo4003.calculator.domain.form.validation;

import ca.ulaval.glo4003.calculator.domain.form.QuoteForm;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuotePositiveCoverageAmountError;

public class PositiveCoverageAmountQuoteFormValidation implements QuoteFormValidation {
  @Override
  public void validate(QuoteForm quoteForm) {
    Amount coverageAmount = quoteForm.getPersonalProperty().getCoverageAmount();
    if (coverageAmount.isSmallerOrEqual(Amount.ZERO)) {
      throw new QuotePositiveCoverageAmountError();
    }
  }
}
