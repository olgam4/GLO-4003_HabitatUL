package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteNotYetPersistedException extends QuoteException {
  // MIGHT NOT WANT TO REDIRECT THIS UP TO THE USER
  // TODO: should not have an ERROR/MESSAGE
  // TODO: should be signed exception
  private static final String ERROR = "QUOTE_NOT_YET_PERSISTED";
  private static final String MESSAGE = "quote with id <%s> has never been persisted";

  private final QuoteId quoteId;

  public QuoteNotYetPersistedException(QuoteId quoteId) {
    this.quoteId = quoteId;
  }

  public QuoteId getQuoteId() {
    return quoteId;
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
