package ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.premium.formula.BasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;

public class CoverageModificationPremiumFormula
    extends PremiumFormula<CoverageModificationPremiumInput> {
  public CoverageModificationPremiumFormula(
      BasePremiumCalculator<CoverageModificationPremiumInput> basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
