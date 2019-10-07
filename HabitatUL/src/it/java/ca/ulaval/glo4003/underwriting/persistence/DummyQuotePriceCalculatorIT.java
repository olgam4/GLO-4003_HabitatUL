package ca.ulaval.glo4003.underwriting.persistence;

import ca.ulaval.glo4003.generator.price.PriceGenerator;
import ca.ulaval.glo4003.underwriting.domain.QuotePriceCalculator;
import ca.ulaval.glo4003.underwriting.domain.QuotePriceCalculatorIT;
import ca.ulaval.glo4003.underwriting.infrastructure.DummyQuotePriceCalculator;

public class DummyQuotePriceCalculatorIT extends QuotePriceCalculatorIT {
  @Override
  public QuotePriceCalculator createSubject() {
    return new DummyQuotePriceCalculator(PriceGenerator.create());
  }
}
