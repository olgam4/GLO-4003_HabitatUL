package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class BicycleEndorsementPremiumInput extends ValueObject {
  private final Amount bicyclePrice;

  public BicycleEndorsementPremiumInput(Amount bicyclePrice) {
    this.bicyclePrice = bicyclePrice;
  }

  public Amount getBicyclePrice() {
    return bicyclePrice;
  }
}
