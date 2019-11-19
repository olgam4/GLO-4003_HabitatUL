package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragemodification.CoverageModificationFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.IncreasedCoverageAmountError;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.PERSONAL_PROPERTY;

public class IncreasedCoverageAmountFormValidationPart
    implements CoverageModificationFormValidationPart {
  @Override
  public void validate(CoverageModificationForm form) {
    Amount currentCoverageAmount =
        form.getCurrentCoverageDetails().getCoverageAmount(PERSONAL_PROPERTY);
    Amount requestedCoverageAmount = form.getCoverageAmount();
    if (requestedCoverageAmount == null) return;
    if (requestedCoverageAmount.isSmallerOrEqual(currentCoverageAmount)) {
      throw new IncreasedCoverageAmountError();
    }
  }
}
