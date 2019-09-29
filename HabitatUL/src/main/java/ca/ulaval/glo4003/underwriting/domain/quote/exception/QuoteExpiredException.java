package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteExpiredException extends QuoteException {
  private static final String ERROR = "EXPIRED_QUOTE";
  private static final String MESSAGE = "quote with id <%s> is expired";

  private final QuoteId quoteId;

  public QuoteExpiredException(QuoteId quoteId) {
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
