package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class MaximumQuotePriceAdjustment extends QuotePriceAdjustment {
  private final Money maximum;

  public MaximumQuotePriceAdjustment(Money maximum) {
    this.maximum = maximum;
  }

  @Override
  public Money apply(Money price) {
    return Money.min(maximum, price);
  }
}
