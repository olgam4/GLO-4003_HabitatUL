package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteExpiredException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class Quote extends AggregateRoot {
  private QuoteId quoteId;
  private Premium premium;
  private QuoteForm quoteForm;
  private DateTime expirationDate;
  private Boolean purchased;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      Premium premium,
      QuoteForm quoteForm,
      DateTime expirationDate,
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

  public DateTime getExpirationDate() {
    return expirationDate;
  }

  public void purchase() {
    if (isPurchased()) throw new QuoteAlreadyPurchasedException(quoteId);
    if (isExpired()) throw new QuoteExpiredException(quoteId);

    purchased = true;
    registerQuotePurchaseEvent();
  }

  private void registerQuotePurchaseEvent() {
    registerEvent(new QuotePurchasedEvent(quoteId, premium, quoteForm, clockProvider));
  }

  public boolean isPurchased() {
    return purchased;
  }

  public boolean isExpired() {
    return DateTime.now(clockProvider.getClock()).isAfter(expirationDate);
  }

  public Premium getPremium() {
    return premium;
  }
}
