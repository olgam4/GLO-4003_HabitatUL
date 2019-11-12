package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikeBasePremiumCalculatorIT;

public class HardCodedBikeBasePremiumCalculatorIT extends BikeBasePremiumCalculatorIT {
  @Override
  public BikeBasePremiumCalculator createSubject() {
    return new HardCodedBikeBasePremiumCalculator();
  }
}
