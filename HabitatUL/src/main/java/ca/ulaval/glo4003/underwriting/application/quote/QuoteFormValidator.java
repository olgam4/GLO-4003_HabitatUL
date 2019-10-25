package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.validation.*;

import java.util.ArrayList;
import java.util.List;

public class QuoteFormValidator {
  private List<QuoteFormValidation> quoteFormValidations;

  public QuoteFormValidator() {
    this(assembleValidations());
  }

  public QuoteFormValidator(List<QuoteFormValidation> quoteFormValidations) {
    this.quoteFormValidations = quoteFormValidations;
  }

  private static List<QuoteFormValidation> assembleValidations() {
    List<QuoteFormValidation> quoteFormValidations = new ArrayList<>();
    quoteFormValidations.add(new StudentNamedInsuredQuoteFormValidation());
    quoteFormValidations.add(
        new EffectiveDateQuoteFormValidation(
            ServiceLocator.resolve(QuoteEffectivePeriodProvider.class),
            ServiceLocator.resolve(ClockProvider.class)));
    quoteFormValidations.add(new CivilLiabilityLimitQuoteFormValidation());
    quoteFormValidations.add(new DifferentAdditionalInsuredQuoteFormValidation());
    quoteFormValidations.add(
        new UlRegistrationQuoteFormValidation(ServiceLocator.resolve(UlRegistrarOffice.class)));
    // TODO: add other validations
    return quoteFormValidations;
  }

  public void validate(QuoteForm quoteForm) {
    quoteFormValidations.forEach(quoteFormValidation -> quoteFormValidation.validate(quoteForm));
  }
}
