package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteNotYetPersistedException extends QuoteException {
  public QuoteNotYetPersistedException(QuoteId quoteId) {
    super(quoteId);
  }
}
