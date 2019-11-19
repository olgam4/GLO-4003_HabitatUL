package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;

public class PositiveCoverageAmountQuoteFormValidationPart
    extends PositiveCoverageAmountFormValidationPart implements QuoteFormValidationPart {
  @Override
  public void validate(QuoteForm form) {
    validate(form.getPersonalProperty().getCoverageAmount());
  }
}
