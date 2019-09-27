package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class ExpiredQuoteException extends QuoteException {
  public ExpiredQuoteException(QuoteId quoteId) {
    super(quoteId);
  }
}
