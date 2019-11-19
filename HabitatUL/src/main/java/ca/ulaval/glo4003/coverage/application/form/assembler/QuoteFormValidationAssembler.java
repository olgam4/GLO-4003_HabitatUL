package ca.ulaval.glo4003.coverage.application.form.assembler;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.*;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidation;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;

public class QuoteFormValidationAssembler {
  private QuoteFormValidationAssembler() {}

  public static QuoteFormValidation assemble() {
    QuoteFormValidation formValidation = new QuoteFormValidation();
    formValidation.addFormValidationPart(new StudentNamedInsuredFormValidationPart());
    formValidation.addFormValidationPart(
        new EffectiveDateFormValidationPart(
            ServiceLocator.resolve(QuoteEffectivePeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)));
    formValidation.addFormValidationPart(new CivilLiabilityLimitQuoteFormValidationPart());
    formValidation.addFormValidationPart(new DifferentAdditionalInsuredFormValidationPart());
    formValidation.addFormValidationPart(
        new UlRegistrationFormValidationPart(ServiceLocator.resolve(UlRegistrarOffice.class)));
    formValidation.addFormValidationPart(new PositiveCoverageAmountQuoteFormValidationPart());
    formValidation.addFormValidationPart(new PositiveBicyclePriceQuoteFormValidationPart());
    return formValidation;
  }
}
