package ca.ulaval.glo4003.calculator.infrastructure.premium;

import ca.ulaval.glo4003.calculator.domain.premium.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.QuoteBasePremiumCalculatorIT;

public class HardCodedQuoteBasePremiumCalculatorIT extends QuoteBasePremiumCalculatorIT {
  @Override
  public QuoteBasePremiumCalculator createSubject() {
    return new HardCodedQuoteBasePremiumCalculator();
  }
}
