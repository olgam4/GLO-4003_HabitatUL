package ca.ulaval.glo4003.coverage.application.form.assembler;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.*;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidation;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;

public class QuoteFormValidationAssembler {
  private QuoteFormValidationAssembler() {}

  public static QuoteFormValidation assemble() {
    QuoteFormValidation quoteFormValidation = new QuoteFormValidation();
    quoteFormValidation.addFormValidationPart(new StudentNamedInsuredFormValidationPart());
    quoteFormValidation.addFormValidationPart(
        new EffectiveDateFormValidationPart(
            ServiceLocator.resolve(QuoteEffectivePeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)));
    quoteFormValidation.addFormValidationPart(new CivilLiabilityLimitFormValidationPart());
    quoteFormValidation.addFormValidationPart(new DifferentAdditionalInsuredFormValidationPart());
    quoteFormValidation.addFormValidationPart(
        new UlRegistrationFormValidationPart(ServiceLocator.resolve(UlRegistrarOffice.class)));
    quoteFormValidation.addFormValidationPart(new PositiveCoverageAmountFormValidationPart());
    quoteFormValidation.addFormValidationPart(new PositiveBicyclePriceQuoteFormValidationPart());
    return quoteFormValidation;
  }
}
