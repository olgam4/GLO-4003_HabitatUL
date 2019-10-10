package ca.ulaval.glo4003.underwriting.domain.quote.error;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteAlreadyPurchasedError extends QuoteError {
  private static final String ERROR = "QUOTE_ALREADY_PURCHASED";
  private static final String MESSAGE = "sorry, quote with id <%s> has already been purchased";

  private final QuoteId quoteId;

  public QuoteAlreadyPurchasedError(QuoteId quoteId) {
    this.quoteId = quoteId;
  }

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE, quoteId.getValue());
  }
}
