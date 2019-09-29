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
  private Boolean purchased;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      Premium premium,
      QuoteRequest quoteRequest,
      Date expirationDate,
      Boolean purchased,
      ClockProvider clockProvider) {
    this.quoteId = quoteId;
    this.premium = premium;
    this.quoteRequest = quoteRequest;
    this.expirationDate = expirationDate;
    this.purchased = purchased;
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

    purchased = true;
  }

  public boolean isPurchased() {
    return purchased;
  }

  public boolean isExpired() {
    return Date.now(clockProvider.getClock()).isAfter(expirationDate);
  }
}
