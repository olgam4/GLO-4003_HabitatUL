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
  static final int COVERAGE_PERIOD_IN_MONTHS = 12;

  private QuoteId quoteId;
  private Money price;
  private QuoteForm quoteForm;
  private Period effectivePeriod;
  private DateTime expirationDate;
  private Boolean purchased;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      Money price,
      QuoteForm quoteForm,
      DateTime expirationDate,
      Boolean purchased,
      ClockProvider clockProvider) {
    this.quoteId = quoteId;
    this.price = price;
    this.quoteForm = quoteForm;
    this.expirationDate = expirationDate;
    this.effectivePeriod = computeEffectivePeriod(quoteForm);
    this.purchased = purchased;
    this.clockProvider = clockProvider;
  }

  private Period computeEffectivePeriod(QuoteForm quoteForm) {
    Date effectiveDate = quoteForm.getEffectiveDate();
    java.time.Period coveragePeriod = java.time.Period.ofMonths(COVERAGE_PERIOD_IN_MONTHS);
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
