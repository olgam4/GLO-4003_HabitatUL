package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockBasePremiumCalculatorIT;

public class HardCodedQuoteBasicBlockBasePremiumCalculatorIT
    extends QuoteBasicBlockBasePremiumCalculatorIT {
  @Override
  public QuoteBasicBlockBasePremiumCalculator createSubject() {
    return new HardCodedQuoteBasicBlockBasePremiumCalculator();
  }
}
