package ca.ulaval.glo4003.coverage.application.form.assembler;

import ca.ulaval.glo4003.coverage.domain.form.validation.coveragerenewal.CoverageRenewalFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.IncreasedCoverageAmountCoverageRenewalFormValidationPart;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.PositiveCoverageAmountCoverageRenewalFormValidationPart;

public class CoverageRenewalFormValidationAssembler {
  private CoverageRenewalFormValidationAssembler() {}

  public static CoverageRenewalFormValidation assemble() {
    CoverageRenewalFormValidation formValidation = new CoverageRenewalFormValidation();
    formValidation.addFormValidationPart(
        new PositiveCoverageAmountCoverageRenewalFormValidationPart());
    formValidation.addFormValidationPart(
        new IncreasedCoverageAmountCoverageRenewalFormValidationPart());
    return formValidation;
  }
}
