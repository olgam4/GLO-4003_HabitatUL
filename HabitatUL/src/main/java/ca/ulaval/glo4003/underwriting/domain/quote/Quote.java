package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.domain.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteExpiredException;

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

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public Date getExpirationDate() {
    return expirationDate;
  }

  public void purchase() {
    if (isPurchased()) throw new QuoteAlreadyPurchasedException(quoteId);
    if (isExpired()) throw new QuoteExpiredException(quoteId);

    purchaseDate = Date.now(clockProvider.getClock());
  }

  public boolean isPurchased() {
    return !purchaseDate.equals(Date.nullDate());
  }

  public boolean isExpired() {
    return Date.now(clockProvider.getClock()).isAfter(expirationDate);
  }
}
