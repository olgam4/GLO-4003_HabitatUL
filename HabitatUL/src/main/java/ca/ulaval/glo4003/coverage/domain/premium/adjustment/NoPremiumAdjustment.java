package ca.ulaval.glo4003.coverage.domain.premium.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class NoPremiumAdjustment extends PremiumAdjustment {
  @Override
  public Money apply(Money premium) {
    return premium;
  }
}
