package ca.ulaval.glo4003.calculator.infrastructure.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuoteBasePremiumCalculatorIT;

public class HardCodedQuoteBasePremiumCalculatorIT extends QuoteBasePremiumCalculatorIT {
  @Override
  public QuoteBasePremiumCalculator createSubject() {
    return new HardCodedQuoteBasePremiumCalculator();
  }
}
