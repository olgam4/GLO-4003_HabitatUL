package ca.ulaval.glo4003.coverage.domain.form.validation.error;

public class QuoteEffectiveDateError extends FormValidationError {
  private static final String ERROR = "QUOTE_EFFECTIVE_DATE";
  private static final String MESSAGE = "sorry, quote can only be issued for the incoming year";

  public QuoteEffectiveDateError() {
    super(ERROR, MESSAGE);
  }
}
