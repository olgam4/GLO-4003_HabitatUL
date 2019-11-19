package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragemodification.CoverageModificationFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.IncreasedCivilLiabilityLimitError;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.CIVIL_LIABILITY;

public class IncreasedCivilLiabilityLimitFormValidationPart
    implements CoverageModificationFormValidationPart {
  @Override
  public void validate(CoverageModificationForm form) {
    Amount currentCivilLiabilityLimitAmount =
        form.getCurrentCoverageDetails().getCoverageAmount(CIVIL_LIABILITY);
    CivilLiabilityLimit requestedCivilLiabilityLimit = form.getCivilLiabilityLimit();
    if (requestedCivilLiabilityLimit == null) return;
    Amount requestedCivilLiabilityLimitAmount = requestedCivilLiabilityLimit.getAmount();
    if (requestedCivilLiabilityLimitAmount.isSmallerOrEqual(currentCivilLiabilityLimitAmount)) {
      throw new IncreasedCivilLiabilityLimitError();
    }
  }
}
