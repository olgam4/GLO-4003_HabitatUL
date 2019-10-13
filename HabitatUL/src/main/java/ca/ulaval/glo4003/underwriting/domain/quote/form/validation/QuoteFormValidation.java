package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public interface QuoteFormValidation {
  void validate(QuoteForm quoteForm);
}
