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
  private Period effectivePeriod;
  private Money price;
  private DateTime expirationDate;
  private Boolean purchased;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      QuoteForm quoteForm,
      java.time.Period coveragePeriod,
      Money price,
      DateTime expirationDate,
      Boolean purchased,
      ClockProvider clockProvider) {
    this.quoteId = quoteId;
    this.quoteForm = quoteForm;
    this.effectivePeriod = computeEffectivePeriod(quoteForm, coveragePeriod);
    this.price = price;
    this.expirationDate = expirationDate;
    this.purchased = purchased;
    this.clockProvider = clockProvider;
  }

  private Period computeEffectivePeriod(QuoteForm quoteForm, java.time.Period coveragePeriod) {
    Date effectiveDate = quoteForm.getEffectiveDate();
    Date coverageEndDate = effectiveDate.plus(coveragePeriod);
    return new Period(effectiveDate, coverageEndDate);
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public Money getPrice() {
    return price;
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

  public void purchase() {
    if (isPurchased()) throw new QuoteAlreadyPurchasedError(quoteId);
    if (isExpired()) throw new QuoteExpiredError(quoteId);

    purchased = true;
    registerQuotePurchaseEvent();
  }

  private void registerQuotePurchaseEvent() {
    registerEvent(new QuotePurchasedEvent(quoteId, price, quoteForm));
  }

  public boolean isPurchased() {
    return purchased;
  }

  public boolean isExpired() {
    return DateTime.now(clockProvider.getClock()).isAfter(expirationDate);
  }
}
