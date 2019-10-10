package ca.ulaval.glo4003.underwriting.application.quote.error;

public class InvalidEffectiveDateError extends QuoteAppServiceError {
  private static final String ERROR = "INVALID_EFFECTIVE_DATE";
  private static final String MESSAGE = "sorry, quote can only be issued for the incoming year";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE);
  }
}
