package ca.ulaval.glo4003.calculator.domain.premium.formula.bike;

import ca.ulaval.glo4003.calculator.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.helper.calculator.BikePremiumInputGenerator;

public class BikeEndorsementPremiumFormulaTest extends PremiumFormulaTest<BikePremiumInput> {
  @Override
  public BikePremiumInput createInput() {
    return BikePremiumInputGenerator.create();
  }
}
