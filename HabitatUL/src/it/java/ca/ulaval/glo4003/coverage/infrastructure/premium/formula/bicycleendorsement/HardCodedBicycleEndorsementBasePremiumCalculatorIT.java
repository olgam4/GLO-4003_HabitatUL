package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculatorIT;

public class HardCodedBicycleEndorsementBasePremiumCalculatorIT
    extends BicycleEndorsementBasePremiumCalculatorIT {
  @Override
  public BicycleEndorsementBasePremiumCalculator createSubject() {
    return new HardCodedBicycleEndorsementBasePremiumCalculator();
  }
}
