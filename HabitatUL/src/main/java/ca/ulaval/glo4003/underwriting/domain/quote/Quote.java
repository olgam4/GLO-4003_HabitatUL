package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteAlreadyPurchasedError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteExpiredError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class Quote extends AggregateRoot {
  private QuoteId quoteId;
  private QuoteForm quoteForm;
  private DateTime expirationDate;
  private Period effectivePeriod;
  private Money premium;
  private Boolean purchased;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      QuoteForm quoteForm,
      DateTime expirationDate,
      Period effectivePeriod,
      Money premium,
      Boolean purchased,
      ClockProvider clockProvider) {
    this.quoteId = quoteId;
    this.quoteForm = quoteForm;
    this.expirationDate = expirationDate;
    this.effectivePeriod = effectivePeriod;
    this.premium = premium;
    this.purchased = purchased;
    this.clockProvider = clockProvider;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public QuoteForm getQuoteForm() {
    return quoteForm;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }

  public Period getEffectivePeriod() {
    return effectivePeriod;
  }

  public Money getPremium() {
    return premium;
  }

  public boolean isExpired() {
    return DateTime.now(clockProvider.getClock()).isAfter(expirationDate);
  }

  public boolean isPurchased() {
    return purchased;
  }

  public void purchase() {
    if (isPurchased()) throw new QuoteAlreadyPurchasedError(quoteId);
    if (isExpired()) throw new QuoteExpiredError(quoteId);

    purchased = true;
    registerQuotePurchaseEvent();
  }

  private void registerQuotePurchaseEvent() {
    Date now = Date.now(clockProvider.getClock());
    registerEvent(new QuotePurchasedEvent(quoteId, quoteForm, effectivePeriod, premium, now));
  }
}
