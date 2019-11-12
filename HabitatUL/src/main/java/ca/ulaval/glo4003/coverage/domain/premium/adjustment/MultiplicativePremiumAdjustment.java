package ca.ulaval.glo4003.coverage.domain.premium.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class MultiplicativePremiumAdjustment extends PremiumAdjustment {
  private final double factor;

  public MultiplicativePremiumAdjustment(double factor) {
    this.factor = factor;
  }

  @Override
  public Money apply(Money premium) {
    return premium.multiply(factor);
  }
}
