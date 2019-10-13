package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuotePurchasedEvent extends Event {
  private QuoteId quoteId;
  private Money price;
  private QuoteForm quoteForm;

  public QuotePurchasedEvent(QuoteId quoteId, Money price, QuoteForm quoteForm) {
    this.quoteId = quoteId;
    this.price = price;
    this.quoteForm = quoteForm;
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
}
