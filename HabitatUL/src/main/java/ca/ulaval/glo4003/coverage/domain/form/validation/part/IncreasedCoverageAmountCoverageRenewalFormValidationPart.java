package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragerenewal.CoverageRenewalFormValidationPart;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.PERSONAL_PROPERTY;

public class IncreasedCoverageAmountCoverageRenewalFormValidationPart
    extends IncreasedCoverageAmountFormValidationPart implements CoverageRenewalFormValidationPart {
  @Override
  public void validate(CoverageRenewalForm form) {
    Amount currentCoverageAmount =
        form.getCurrentCoverageDetails().getCoverageAmount(PERSONAL_PROPERTY);
    Amount requestedCoverageAmount = form.getCoverageAmount();
    validate(currentCoverageAmount, requestedCoverageAmount);
  }
}
