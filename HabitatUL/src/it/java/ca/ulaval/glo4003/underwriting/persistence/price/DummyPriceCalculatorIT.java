package ca.ulaval.glo4003.underwriting.persistence.price;

import ca.ulaval.glo4003.generator.price.PriceGenerator;
import ca.ulaval.glo4003.underwriting.domain.price.PriceCalculatorIT;
import ca.ulaval.glo4003.underwriting.domain.price.QuotePriceCalculator;
import ca.ulaval.glo4003.underwriting.infrastructure.price.DummyQuotePriceCalculator;

public class DummyPriceCalculatorIT extends PriceCalculatorIT {
  @Override
  public QuotePriceCalculator createSubject() {
    return new DummyQuotePriceCalculator(PriceGenerator.create());
  }
}
