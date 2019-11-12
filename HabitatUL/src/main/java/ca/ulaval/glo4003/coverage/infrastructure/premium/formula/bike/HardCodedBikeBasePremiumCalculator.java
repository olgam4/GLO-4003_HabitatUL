package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class HardCodedBikeBasePremiumCalculator implements BikeBasePremiumCalculator {
  static final Money HARDCODED_PREMIUM = new Money(Amount.ZERO);

  @Override
  public Money compute(BikePremiumInput quotePremiumInput) {
    return HARDCODED_PREMIUM;
  }
}
