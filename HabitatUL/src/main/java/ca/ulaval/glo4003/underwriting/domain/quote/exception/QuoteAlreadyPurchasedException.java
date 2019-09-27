package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteAlreadyPurchasedException extends QuoteException {
  public QuoteAlreadyPurchasedException(QuoteId quoteId) {
    super(quoteId);
  }
}
