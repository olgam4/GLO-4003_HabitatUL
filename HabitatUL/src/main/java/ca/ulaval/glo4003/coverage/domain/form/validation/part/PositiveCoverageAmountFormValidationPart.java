package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveCoverageAmountError;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PositiveCoverageAmountFormValidationPart implements QuoteFormValidationPart {
  @Override
  public void validate(QuoteForm form) {
    Amount coverageAmount = form.getPersonalProperty().getCoverageAmount();
    if (coverageAmount.isSmallerOrEqual(Amount.ZERO)) {
      throw new PositiveCoverageAmountError();
    }
  }
}
