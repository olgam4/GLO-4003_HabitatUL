package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteExpiredException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class Quote {
  private QuoteId quoteId;
  private Premium premium;
  private QuoteForm quoteForm;
  private Date expirationDate;
  private Boolean purchased;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      Premium premium,
      QuoteForm quoteForm,
      Date expirationDate,
      Boolean purchased,
      ClockProvider clockProvider) {
    this.quoteId = quoteId;
    this.premium = premium;
    this.quoteForm = quoteForm;
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

  public Premium getPremium() {
    return premium;
  }
}
