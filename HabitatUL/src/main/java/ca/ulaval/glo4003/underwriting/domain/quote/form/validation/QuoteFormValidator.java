package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

import java.util.ArrayList;
import java.util.List;

public class QuoteFormValidator {
  private List<QuoteFormValidation> quoteFormValidations = new ArrayList<>();

  public QuoteFormValidator(ClockProvider clockProvider) {
    quoteFormValidations.add(new EffectiveDateQuoteFormValidation(clockProvider));
    // TODO: add other validations here
  }

  public void validate(QuoteForm quoteForm) {
    quoteFormValidations.forEach(quoteFormValidation -> quoteFormValidation.validate(quoteForm));
  }
}
