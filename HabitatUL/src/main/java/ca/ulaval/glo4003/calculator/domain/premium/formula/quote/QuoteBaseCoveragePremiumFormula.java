package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.PremiumFormula;

public class QuoteBaseCoveragePremiumFormula extends PremiumFormula<QuotePremiumInput> {
  public QuoteBaseCoveragePremiumFormula(QuoteBasePremiumCalculator basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
