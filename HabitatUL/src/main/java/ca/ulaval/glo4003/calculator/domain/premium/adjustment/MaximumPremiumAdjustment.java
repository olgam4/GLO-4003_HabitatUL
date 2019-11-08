package ca.ulaval.glo4003.calculator.domain.premium.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class MaximumPremiumAdjustment extends PremiumAdjustment {
  private final Money maximum;

  public MaximumPremiumAdjustment(Money maximum) {
    this.maximum = maximum;
  }

  @Override
  public Money apply(Money premium) {
    return Money.min(maximum, premium);
  }
}
