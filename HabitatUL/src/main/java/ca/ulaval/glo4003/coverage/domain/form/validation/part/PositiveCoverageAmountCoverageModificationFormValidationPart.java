package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragemodification.CoverageModificationFormValidationPart;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PositiveCoverageAmountCoverageModificationFormValidationPart
    extends PositiveCoverageAmountFormValidationPart
    implements CoverageModificationFormValidationPart {
  @Override
  public void validate(CoverageModificationForm form) {
    Amount coverageAmount = form.getCoverageAmount();
    if (coverageAmount != null) validate(coverageAmount);
  }
}
