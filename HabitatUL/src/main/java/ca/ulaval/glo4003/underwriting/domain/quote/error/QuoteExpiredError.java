package ca.ulaval.glo4003.underwriting.domain.quote.error;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteExpiredError extends QuoteError {
  private static final String ERROR = "QUOTE_EXPIRED";
  private static final String MESSAGE = "sorry, quote with id <%s> is expired";

  private final QuoteId quoteId;

  public QuoteExpiredError(QuoteId quoteId) {
    this.quoteId = quoteId;
  }

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE, quoteId.getValue());
  }
}
