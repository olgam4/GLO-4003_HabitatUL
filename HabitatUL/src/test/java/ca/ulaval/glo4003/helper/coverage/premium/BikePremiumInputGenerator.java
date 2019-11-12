package ca.ulaval.glo4003.helper.coverage.premium;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.BikeGenerator;

public class BikePremiumInputGenerator {
  private BikePremiumInputGenerator() {}

  public static BikePremiumInput create() {
    return new BikePremiumInput(BikeGenerator.createBikePrice());
  }
}
