package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;

public class QuoteBasicBlockPremiumFormula extends PremiumFormula<QuotePremiumInput> {
  public QuoteBasicBlockPremiumFormula(QuoteBasePremiumCalculator basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
