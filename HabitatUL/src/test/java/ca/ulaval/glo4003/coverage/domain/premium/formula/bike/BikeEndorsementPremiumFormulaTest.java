package ca.ulaval.glo4003.coverage.domain.premium.formula.bike;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.helper.coverage.premium.BikePremiumInputGenerator;

public class BikeEndorsementPremiumFormulaTest extends PremiumFormulaTest<BikePremiumInput> {
  @Override
  public BikePremiumInput createInput() {
    return BikePremiumInputGenerator.create();
  }
}
