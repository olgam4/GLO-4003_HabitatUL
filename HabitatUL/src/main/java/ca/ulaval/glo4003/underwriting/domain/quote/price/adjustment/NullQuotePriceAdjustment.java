package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class NullQuotePriceAdjustment extends QuotePriceAdjustment {
  @Override
  public Money apply(Money price) {
    return Money.ZERO;
  }
}
