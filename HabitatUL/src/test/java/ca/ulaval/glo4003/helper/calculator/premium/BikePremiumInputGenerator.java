package ca.ulaval.glo4003.helper.calculator.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.helper.calculator.form.personalproperty.BikeGenerator;

public class BikePremiumInputGenerator {
  private BikePremiumInputGenerator() {}

  public static BikePremiumInput create() {
    return new BikePremiumInput(BikeGenerator.createBikePrice());
  }
}
