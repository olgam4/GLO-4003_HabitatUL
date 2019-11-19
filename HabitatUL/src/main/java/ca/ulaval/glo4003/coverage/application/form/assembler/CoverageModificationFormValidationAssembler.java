package ca.ulaval.glo4003.coverage.application.form.assembler;

import ca.ulaval.glo4003.coverage.domain.form.validation.coveragemodification.CoverageModificationFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.CivilLiabilityLimitCoverageModificationFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.IncreasedCivilLiabilityLimitFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.IncreasedCoverageAmountFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.PositiveCoverageAmountCoverageModificationFormValidationPart;

public class CoverageModificationFormValidationAssembler {
  private CoverageModificationFormValidationAssembler() {}

  public static CoverageModificationFormValidation assemble() {
    CoverageModificationFormValidation formValidation = new CoverageModificationFormValidation();
    formValidation.addFormValidationPart(
        new PositiveCoverageAmountCoverageModificationFormValidationPart());
    formValidation.addFormValidationPart(
        new CivilLiabilityLimitCoverageModificationFormValidationPart());
    formValidation.addFormValidationPart(new IncreasedCoverageAmountFormValidationPart());
    formValidation.addFormValidationPart(new IncreasedCivilLiabilityLimitFormValidationPart());
    return formValidation;
  }
}
