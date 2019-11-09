package ca.ulaval.glo4003.calculator.domain.premium.formula.bike;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class BikePremiumInput extends ValueObject {
  private final Amount price;

  public BikePremiumInput(Amount price) {
    this.price = price;
  }

  public Amount getPrice() {
    return price;
  }
}
