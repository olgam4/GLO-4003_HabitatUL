package ca.ulaval.glo4003.coverage.application.form;

import ca.ulaval.glo4003.coverage.application.form.assembler.BicycleEndorsementFormValidationAssembler;
import ca.ulaval.glo4003.coverage.application.form.assembler.CoverageModificationFormValidationAssembler;
import ca.ulaval.glo4003.coverage.application.form.assembler.CoverageRenewalFormValidationAssembler;
import ca.ulaval.glo4003.coverage.application.form.assembler.QuoteFormValidationAssembler;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageRenewalForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.bicycleendorsement.BicycleEndorsementFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragemodification.CoverageModificationFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.coveragerenewal.CoverageRenewalFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidation;

public class FormValidator {
  private QuoteFormValidation quoteFormValidation;
  private BicycleEndorsementFormValidation bicycleEndorsementFormValidation;
  private CoverageModificationFormValidation coverageModificationFormValidation;
  private CoverageRenewalFormValidation coverageRenewalFormValidation;

  public FormValidator() {
    this(
        QuoteFormValidationAssembler.assemble(),
        BicycleEndorsementFormValidationAssembler.assemble(),
        CoverageModificationFormValidationAssembler.assemble(),
        CoverageRenewalFormValidationAssembler.assemble());
  }

  public FormValidator(
      QuoteFormValidation quoteFormValidation,
      BicycleEndorsementFormValidation bicycleEndorsementFormValidation,
      CoverageModificationFormValidation coverageModificationFormValidation,
      CoverageRenewalFormValidation coverageRenewalFormValidation) {
    this.quoteFormValidation = quoteFormValidation;
    this.bicycleEndorsementFormValidation = bicycleEndorsementFormValidation;
    this.coverageModificationFormValidation = coverageModificationFormValidation;
    this.coverageRenewalFormValidation = coverageRenewalFormValidation;
  }

  public void validateQuoteForm(QuoteForm quoteForm) {
    quoteFormValidation.validate(quoteForm);
  }

  public void validateBicycleEndorsementForm(BicycleEndorsementForm bicycleEndorsementForm) {
    bicycleEndorsementFormValidation.validate(bicycleEndorsementForm);
  }

  public void validateCoverageModificationForm(CoverageModificationForm coverageModificationForm) {
    coverageModificationFormValidation.validate(coverageModificationForm);
  }

  public void validateCoverageRenewal(CoverageRenewalForm coverageRenewalForm) {
    coverageRenewalFormValidation.validate(coverageRenewalForm);
  }
}
