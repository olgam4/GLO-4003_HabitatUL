package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormula;

public class BicycleEndorsementPremiumFormula
    extends PremiumFormula<BicycleEndorsementPremiumInput> {
  public BicycleEndorsementPremiumFormula(
      BicycleEndorsementBasePremiumCalculator basePremiumCalculator) {
    super(basePremiumCalculator);
  }
}
