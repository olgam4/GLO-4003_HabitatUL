package ca.ulaval.glo4003.coverage.domain.form.validation.quote;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.FormValidationPart;

public interface QuoteFormValidationPart extends FormValidationPart<QuoteForm> {
  void validate(QuoteForm quoteForm);
}
