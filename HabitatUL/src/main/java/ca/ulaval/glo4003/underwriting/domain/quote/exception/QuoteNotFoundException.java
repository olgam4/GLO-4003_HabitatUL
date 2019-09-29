package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteNotFoundException extends QuoteException {
  private static final String ERROR = "QUOTE_NOT_FOUND";
  private static final String MESSAGE = "can't find quote with id <%s>";

  private final QuoteId quoteId;

  public QuoteNotFoundException(QuoteId quoteId) {
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
