package ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeBasePremiumCalculatorIT;

public class HardCodedBikeBasePremiumCalculatorIT extends BikeBasePremiumCalculatorIT {
  @Override
  public BikeBasePremiumCalculator createSubject() {
    return new HardCodedBikeBasePremiumCalculator();
  }
}
