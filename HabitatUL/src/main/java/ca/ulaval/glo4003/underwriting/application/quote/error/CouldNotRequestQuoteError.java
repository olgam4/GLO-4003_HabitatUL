package ca.ulaval.glo4003.underwriting.application.quote.error;

public class CouldNotRequestQuoteError extends QuoteAppServiceError {
  private static final String ERROR = "COULD_NOT_REQUEST_QUOTE";
  private static final String MESSAGE =
      "sorry, something went wrong while trying to request your quote";

  @Override
  public String getError() {
    return ERROR;
  }

  @Override
  public String getMessage() {
    return MESSAGE;
  }
}
