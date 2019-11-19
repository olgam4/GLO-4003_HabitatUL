package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragemodification.CoverageModificationFormValidationPart;

public class CivilLiabilityLimitCoverageModificationFormValidationPart
    extends CivilLiabilityLimitFormValidationPart
    implements CoverageModificationFormValidationPart {
  @Override
  public void validate(CoverageModificationForm form) {
    CivilLiabilityLimit civilLiabilityLimit = form.getCivilLiabilityLimit();
    int numberOfUnits = form.getNumberOfUnits();
    if (civilLiabilityLimit != null) validate(civilLiabilityLimit, numberOfUnits);
  }
}
