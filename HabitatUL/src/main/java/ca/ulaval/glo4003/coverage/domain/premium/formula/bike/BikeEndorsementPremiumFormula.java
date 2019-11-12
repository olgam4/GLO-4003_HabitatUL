package ca.ulaval.glo4003.coverage.domain.premium.formula.bike;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;

public class BikeEndorsementPremiumFormula extends PremiumFormula<BikePremiumInput> {
  public BikeEndorsementPremiumFormula(BikeBasePremiumCalculator basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
