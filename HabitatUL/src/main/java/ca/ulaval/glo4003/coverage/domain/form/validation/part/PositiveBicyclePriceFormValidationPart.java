package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.validation.error.PositiveBicyclePriceError;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public abstract class PositiveBicyclePriceFormValidationPart {
  protected void validate(Amount bicyclePrice) {
    if (bicyclePrice.isSmallerOrEqual(Amount.ZERO)) {
      throw new PositiveBicyclePriceError();
    }
  }
}
