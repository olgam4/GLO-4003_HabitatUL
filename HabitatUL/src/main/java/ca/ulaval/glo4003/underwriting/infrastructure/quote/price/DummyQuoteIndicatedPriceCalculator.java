package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteIndicatedPriceCalculator;

public class DummyQuoteIndicatedPriceCalculator implements QuoteIndicatedPriceCalculator {
  private Money price;

  public DummyQuoteIndicatedPriceCalculator(Money price) {
    this.price = price;
  }

  public Money computeIndicatedQuotePrice(QuoteForm quoteForm) {
    return price;
  }
}
