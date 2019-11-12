package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaTest;
import ca.ulaval.glo4003.helper.coverage.premium.BicycleEndorsementPremiumInputGenerator;

public class BicycleEndorsementPremiumFormulaTest
    extends PremiumFormulaTest<BicycleEndorsementPremiumInput> {
  @Override
  public BicycleEndorsementPremiumInput createInput() {
    return BicycleEndorsementPremiumInputGenerator.createBicycleEndorsementPremiumInput();
  }
}
