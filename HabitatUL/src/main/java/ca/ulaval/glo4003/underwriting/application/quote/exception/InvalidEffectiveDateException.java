package ca.ulaval.glo4003.underwriting.application.quote.exception;

public class InvalidEffectiveDateException extends QuoteAppServiceException {
  private static final String ERROR = "INVALID_EFFECTIVE_DATE";
  private static final String MESSAGE = "quote has invalid effective date";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE);
  }
}
