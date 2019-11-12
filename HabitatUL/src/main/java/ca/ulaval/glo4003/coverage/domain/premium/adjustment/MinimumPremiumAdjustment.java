package ca.ulaval.glo4003.coverage.domain.premium.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class MinimumPremiumAdjustment extends PremiumAdjustment {
  private final Money minimum;

  public MinimumPremiumAdjustment(Money minimum) {
    this.minimum = minimum;
  }

  @Override
  public Money apply(Money premium) {
    return Money.max(minimum, premium);
  }
}
