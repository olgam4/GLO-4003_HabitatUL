package ca.ulaval.glo4003.calculator.domain.premium.formula.bike;

import ca.ulaval.glo4003.calculator.domain.premium.formula.PremiumFormula;

public class BikePremiumFormula extends PremiumFormula<BikePremiumInput> {
  public BikePremiumFormula(BikeBasePremiumCalculator basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
