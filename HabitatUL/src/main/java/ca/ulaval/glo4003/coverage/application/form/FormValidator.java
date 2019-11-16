package ca.ulaval.glo4003.coverage.application.form;

import ca.ulaval.glo4003.coverage.application.form.assembler.BicycleEndorsementFormValidationAssembler;
import ca.ulaval.glo4003.coverage.application.form.assembler.QuoteFormValidationAssembler;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.bicycleendorsement.BicycleEndorsementFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidation;

public class FormValidator {
  private QuoteFormValidation quoteFormValidation;
  private BicycleEndorsementFormValidation bicycleEndorsementFormValidation;

  public FormValidator() {
    this(
        QuoteFormValidationAssembler.assemble(),
        BicycleEndorsementFormValidationAssembler.assemble());
  }

  public FormValidator(
      QuoteFormValidation quoteFormValidation,
      BicycleEndorsementFormValidation bicycleEndorsementFormValidation) {
    this.quoteFormValidation = quoteFormValidation;
    this.bicycleEndorsementFormValidation = bicycleEndorsementFormValidation;
  }

  public void validateQuoteForm(QuoteForm quoteForm) {
    quoteFormValidation.validate(quoteForm);
  }

  public void validateBicycleEndorsementForm(BicycleEndorsementForm bicycleEndorsementForm) {
    bicycleEndorsementFormValidation.validate(bicycleEndorsementForm);
  }
}
