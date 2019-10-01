package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteAlreadyPersistedException extends QuoteException {
  private static final String ERROR = "QUOTE_ALREADY_PERSISTED";
  private static final String MESSAGE = "quote with id <%s> is already persisted";

  private final QuoteId quoteId;

  public QuoteAlreadyPersistedException(QuoteId quoteId) {
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
