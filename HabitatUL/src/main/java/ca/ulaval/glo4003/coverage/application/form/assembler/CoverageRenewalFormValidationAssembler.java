package ca.ulaval.glo4003.coverage.application.form.assembler;

import ca.ulaval.glo4003.coverage.domain.form.validation.coveragerenewal.CoverageRenewalFormValidation;

public class CoverageRenewalFormValidationAssembler {
  private CoverageRenewalFormValidationAssembler() {}

  public static CoverageRenewalFormValidation assemble() {
    return new CoverageRenewalFormValidation();
  }
}
