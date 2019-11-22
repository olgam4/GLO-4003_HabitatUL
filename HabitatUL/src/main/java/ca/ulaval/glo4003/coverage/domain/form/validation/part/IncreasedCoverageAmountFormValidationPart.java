package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.validation.error.IncreasedCoverageAmountError;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public abstract class IncreasedCoverageAmountFormValidationPart {
  public void validate(Amount currentCoverageAmount, Amount requestedCoverageAmount) {
    if (requestedCoverageAmount == null) return;
    if (requestedCoverageAmount.isSmallerOrEqual(currentCoverageAmount)) {
      throw new IncreasedCoverageAmountError();
    }
  }
}
