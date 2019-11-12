package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;

public class QuotePremiumFormula extends PremiumFormula<QuotePremiumInput> {
  public QuotePremiumFormula(QuoteBasePremiumCalculator basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
