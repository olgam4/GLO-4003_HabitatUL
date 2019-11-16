package ca.ulaval.glo4003.coverage.application.form.assembler;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.*;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidation;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;

public class QuoteFormValidationAssembler {
  public static QuoteFormValidation assemble() {
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
}
