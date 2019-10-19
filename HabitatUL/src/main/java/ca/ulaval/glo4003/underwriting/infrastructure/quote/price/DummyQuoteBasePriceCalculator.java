package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteBasePriceCalculator;

public class DummyQuoteBasePriceCalculator implements QuoteBasePriceCalculator {
  private Money price;

  public DummyQuoteBasePriceCalculator(Money price) {
    this.price = price;
  }

  public Money computeQuoteBasePrice(QuoteForm quoteForm) {
    return price;
  }
}
