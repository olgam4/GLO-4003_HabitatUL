package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.domain.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.ExpiredQuoteException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;

public class Quote {
  private QuoteId quoteId;
  private Premium premium;
  private QuoteRequest quoteRequest;
  private Date expirationDate;
  private Date purchaseDate;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      Premium premium,
      QuoteRequest quoteRequest,
      Date expirationDate,
      Date purchaseDate,
      ClockProvider clockProvider) {
    this.quoteId = quoteId;
    this.premium = premium;
    this.quoteRequest = quoteRequest;
    this.expirationDate = expirationDate;
    this.purchaseDate = purchaseDate;
    this.clockProvider = clockProvider;
  }

  public void purchase() {
    if (isPurchased()) throw new QuoteAlreadyPurchasedException();
    if (isExpired()) throw new ExpiredQuoteException();

    purchaseDate = Date.now(clockProvider.getClock());
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public boolean isPurchased() {
    return !purchaseDate.equals(Date.nullDate());
  }

  public boolean isExpired() {
    return Date.now(clockProvider.getClock()).isAfter(expirationDate);
  }
}
