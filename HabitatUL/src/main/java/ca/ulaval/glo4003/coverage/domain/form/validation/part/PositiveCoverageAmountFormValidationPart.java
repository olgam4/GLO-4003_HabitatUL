package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveCoverageAmountError;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public abstract class PositiveCoverageAmountFormValidationPart {
  public void validate(Amount coverageAmount) {
    if (coverageAmount.isSmallerOrEqual(Amount.ZERO)) {
      throw new PositiveCoverageAmountError();
    }
  }
}
