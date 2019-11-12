package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasePremiumCalculatorIT;

public class HardCodedQuoteBasePremiumCalculatorIT extends QuoteBasePremiumCalculatorIT {
  @Override
  public QuoteBasePremiumCalculator createSubject() {
    return new HardCodedQuoteBasePremiumCalculator();
  }
}
