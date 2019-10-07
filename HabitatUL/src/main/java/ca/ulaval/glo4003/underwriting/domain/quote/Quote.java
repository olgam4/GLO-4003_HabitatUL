package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.shared.domain.Period;
import ca.ulaval.glo4003.underwriting.domain.price.Price;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPurchasedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteExpiredException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class Quote extends AggregateRoot {
  static final int COVERAGE_PERIOD_IN_MONTHS = 12;

  private QuoteId quoteId;
  private Price price;
  private QuoteForm quoteForm;
  private Period effectivePeriod;
  private DateTime expirationDate;
  private Boolean purchased;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      Price price,
      QuoteForm quoteForm,
      DateTime expirationDate,
      Boolean purchased,
      ClockProvider clockProvider) {
    this.quoteId = quoteId;
    this.price = price;
    this.quoteForm = quoteForm;
    this.effectivePeriod = computeEffectivePeriod(quoteForm);
    this.expirationDate = expirationDate;
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

  public Price getPrice() {
    return price;
  }

  public Period getEffectivePeriod() {
    return effectivePeriod;
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
    registerEvent(new QuotePurchasedEvent(quoteId, price, quoteForm, clockProvider));
  }

  public boolean isPurchased() {
    return purchased;
  }

  public boolean isExpired() {
    return DateTime.now(clockProvider.getClock()).isAfter(expirationDate);
  }
}
