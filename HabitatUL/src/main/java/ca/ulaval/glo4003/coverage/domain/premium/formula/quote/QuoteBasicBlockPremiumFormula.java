package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.BasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;

public class QuoteBasicBlockPremiumFormula extends PremiumFormula<QuotePremiumInput> {
  public QuoteBasicBlockPremiumFormula(
      BasePremiumCalculator<QuotePremiumInput> basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
