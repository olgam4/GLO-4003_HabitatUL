package ca.ulaval.glo4003.coverage.application.form;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidation;
import ca.ulaval.glo4003.coverage.domain.form.validationpart.*;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;

public class FormValidator {
  private QuoteFormValidation quoteFormValidation;

  public FormValidator() {
    this(assembleQuoteFormValidation());
  }

  public FormValidator(QuoteFormValidation quoteFormValidation) {
    this.quoteFormValidation = quoteFormValidation;
  }

  private static QuoteFormValidation assembleQuoteFormValidation() {
    QuoteFormValidation quoteFormValidation = new QuoteFormValidation();
    quoteFormValidation.addValidationPart(new StudentNamedInsuredFormValidationPart());
    quoteFormValidation.addValidationPart(
        new EffectiveDateFormValidationPart(
            ServiceLocator.resolve(QuoteEffectivePeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)));
    quoteFormValidation.addValidationPart(new CivilLiabilityLimitFormValidationPart());
    quoteFormValidation.addValidationPart(new DifferentAdditionalInsuredFormValidationPart());
    quoteFormValidation.addValidationPart(
        new UlRegistrationFormValidationPart(ServiceLocator.resolve(UlRegistrarOffice.class)));
    quoteFormValidation.addValidationPart(new PositiveCoverageAmountFormValidationPart());
    return quoteFormValidation;
  }

  public void validateQuoteForm(QuoteForm quoteForm) {
    quoteFormValidation.validate(quoteForm);
  }
}
