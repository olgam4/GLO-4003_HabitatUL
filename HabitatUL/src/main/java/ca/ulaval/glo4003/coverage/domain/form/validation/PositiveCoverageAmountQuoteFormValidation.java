package ca.ulaval.glo4003.coverage.domain.form.validation;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveCoverageAmountError;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PositiveCoverageAmountQuoteFormValidation implements QuoteFormValidation {
  @Override
  public void validate(QuoteForm quoteForm) {
    Amount coverageAmount = quoteForm.getPersonalProperty().getCoverageAmount();
    if (coverageAmount.isSmallerOrEqual(Amount.ZERO)) {
      throw new PositiveCoverageAmountError();
    }
  }
}
