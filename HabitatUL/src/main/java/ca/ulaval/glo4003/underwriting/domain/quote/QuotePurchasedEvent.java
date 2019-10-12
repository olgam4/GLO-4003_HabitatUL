package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.underwriting.domain.price.Price;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuotePurchasedEvent extends Event {
  private QuoteId quoteId;
  private Price price;
  private QuoteForm quoteForm;

  public QuotePurchasedEvent(QuoteId quoteId, Price price, QuoteForm quoteForm) {
    this.quoteId = quoteId;
    this.price = price;
    this.quoteForm = quoteForm;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public Price getPrice() {
    return price;
  }

  public QuoteForm getQuoteForm() {
    return quoteForm;
  }
}
