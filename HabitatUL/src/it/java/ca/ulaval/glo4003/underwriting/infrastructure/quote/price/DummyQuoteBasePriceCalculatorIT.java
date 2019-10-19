package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteBasePriceCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.QuoteBasePriceCalculatorIT;

public class DummyQuoteBasePriceCalculatorIT extends QuoteBasePriceCalculatorIT {
  @Override
  public QuoteBasePriceCalculator createSubject() {
    return new DummyQuoteBasePriceCalculator(MoneyGenerator.create());
  }
}
