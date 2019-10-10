package ca.ulaval.glo4003.underwriting.application.quote.error;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteNotFoundError extends QuoteAppServiceError {
  private static final String ERROR = "QUOTE_NOT_FOUND";
  private static final String MESSAGE = "sorry, can't find quote with id <%s>";

  private final QuoteId quoteId;

  public QuoteNotFoundError(QuoteId quoteId) {
    this.quoteId = quoteId;
  }

  @Override
  public String getError() {
    return ERROR;
  }

  @Override
  public String getMessage() {
    return String.format(MESSAGE, quoteId.getValue());
  }
}
