package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class MinimumQuotePriceAdjustment extends QuotePriceAdjustment {
  private final Money minimum;

  public MinimumQuotePriceAdjustment(Money minimum) {
    this.minimum = minimum;
  }

  @Override
  public Money apply(Money price) {
    return Money.max(minimum, price);
  }
}
