package ca.ulaval.glo4003.helper.calculator;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;

public class BikePremiumInputGenerator {
  private BikePremiumInputGenerator() {}

  public static BikePremiumInput create() {
    return new BikePremiumInput(BikeGenerator.createBikePrice());
  }
}
