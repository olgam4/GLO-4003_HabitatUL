package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteAlreadyPurchasedException extends QuoteException {
  private static final String ERROR = "QUOTE_ALREADY_PURCHASED";
  private static final String MESSAGE = "quote with id <%s> has already been purchased";

  private final QuoteId quoteId;

  public QuoteAlreadyPurchasedException(QuoteId quoteId) {
    this.quoteId = quoteId;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE, getQuoteId().getValue());
  }
}
