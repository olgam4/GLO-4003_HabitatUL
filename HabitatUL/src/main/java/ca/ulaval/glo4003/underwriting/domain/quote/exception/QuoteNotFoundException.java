package ca.ulaval.glo4003.underwriting.domain.quote.exception;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteNotFoundException extends QuoteException {
  public QuoteNotFoundException(QuoteId quoteId) {
    super(quoteId);
  }
}
