package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuotePurchasedEvent extends Event {
  private final QuoteId quoteId;
  private final QuoteForm quoteForm;
  private final Period effectivePeriod;
  private final Money premium;
  private final Date purchaseDate;

  public QuotePurchasedEvent(
      QuoteId quoteId,
      QuoteForm quoteForm,
      Period effectivePeriod,
      Money premium,
      Date purchaseDate) {
    this.quoteId = quoteId;
    this.premium = premium;
    this.quoteForm = quoteForm;
    this.effectivePeriod = effectivePeriod;
    this.purchaseDate = purchaseDate;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public QuoteForm getQuoteForm() {
    return quoteForm;
  }

  public Period getEffectivePeriod() {
    return effectivePeriod;
  }

  public Money getPremium() {
    return premium;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }
}
