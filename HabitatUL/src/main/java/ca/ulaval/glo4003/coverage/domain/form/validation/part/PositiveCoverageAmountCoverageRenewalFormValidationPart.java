package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragerenewal.CoverageRenewalFormValidationPart;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class PositiveCoverageAmountCoverageRenewalFormValidationPart
    extends PositiveCoverageAmountFormValidationPart implements CoverageRenewalFormValidationPart {
  @Override
  public void validate(CoverageRenewalForm form) {
    Amount coverageAmount = form.getCoverageAmount();
    if (coverageAmount != null) validate(coverageAmount);
  }
}
