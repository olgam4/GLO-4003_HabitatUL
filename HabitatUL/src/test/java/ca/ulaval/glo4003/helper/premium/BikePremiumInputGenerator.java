package ca.ulaval.glo4003.helper.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class BikePremiumInputGenerator {
  private BikePremiumInputGenerator() {}

  public static BikePremiumInput create() {
    return new BikePremiumInput(createBikePrice());
  }

  public static Amount createBikePrice() {
    return MoneyGenerator.createAmount();
  }
}
