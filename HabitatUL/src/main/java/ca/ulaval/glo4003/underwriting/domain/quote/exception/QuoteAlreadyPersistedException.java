package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteAlreadyPersistedException extends QuoteException {
  // MIGHT NOT WANT TO REDIRECT THIS UP TO THE USER
  // TODO: should not have an ERROR/MESSAGE
  // TODO: should be signed exception
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
