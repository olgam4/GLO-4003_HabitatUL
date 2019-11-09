package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.PremiumFormula;

public class QuotePremiumFormula extends PremiumFormula<QuotePremiumInput> {
  public QuotePremiumFormula(QuoteBasePremiumCalculator basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
