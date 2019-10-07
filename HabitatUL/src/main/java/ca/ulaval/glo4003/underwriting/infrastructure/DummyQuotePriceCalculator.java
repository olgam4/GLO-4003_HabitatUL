package ca.ulaval.glo4003.underwriting.infrastructure;

import ca.ulaval.glo4003.underwriting.domain.QuotePriceCalculator;
import ca.ulaval.glo4003.underwriting.domain.price.Price;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class DummyQuotePriceCalculator implements QuotePriceCalculator {
  private Price price;

  public DummyQuotePriceCalculator(Price dummyPrice) {
    this.price = dummyPrice;
  }

  @Override
  public Price computeQuotePrice(QuoteForm quoteForm) {
    return price;
  }
}
