package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.generator.money.MoneyGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteIndicatedPriceCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteIndicatedPriceCalculatorIT;

public class DummyQuoteIndicatedPriceCalculatorIT extends QuoteIndicatedPriceCalculatorIT {
  @Override
  public QuoteIndicatedPriceCalculator createSubject() {
    return new DummyQuoteIndicatedPriceCalculator(MoneyGenerator.create());
  }
}
