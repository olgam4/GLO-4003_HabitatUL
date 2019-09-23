package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.Date;
import ca.ulaval.glo4003.shared.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.ExpiredQuoteException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;

public class Quote {
  private QuoteId quoteId;
  private Premium premium;
  private QuoteRequest quoteRequest;
  private Date expirationDate;
  private Date purchaseDate;

  public Quote(
      QuoteId quoteId,
      Premium premium,
      QuoteRequest quoteRequest,
      Date expirationDate,
      Date purchaseDate) {
    this.quoteId = quoteId;
    this.premium = premium;
    this.quoteRequest = quoteRequest;
    this.expirationDate = expirationDate;
    this.purchaseDate = purchaseDate;
  }

  public void purchase() {
    if (isPurchased()) throw new QuoteAlreadyPurchasedException();
    if (isExpired()) throw new ExpiredQuoteException();

    purchaseDate = Date.now();
  }

  public boolean isPurchased() {
    return !purchaseDate.equals(Date.nullDate());
  }

  private boolean isExpired() {
    return Date.now().isAfter(expirationDate);
  }
}
