package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class BicycleEndorsementPremiumInput extends ValueObject {
  private final Amount price;

  public BicycleEndorsementPremiumInput(Amount price) {
    this.price = price;
  }

  public Amount getPrice() {
    return price;
  }
}
