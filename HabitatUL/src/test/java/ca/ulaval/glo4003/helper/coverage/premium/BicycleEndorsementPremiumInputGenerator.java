package ca.ulaval.glo4003.helper.coverage.premium;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator;

public class BicycleEndorsementPremiumInputGenerator {
  private BicycleEndorsementPremiumInputGenerator() {}

  public static BicycleEndorsementPremiumInput createBicycleEndorsementPremiumInput() {
    return new BicycleEndorsementPremiumInput(BicycleGenerator.createBicyclePrice());
  }
}
