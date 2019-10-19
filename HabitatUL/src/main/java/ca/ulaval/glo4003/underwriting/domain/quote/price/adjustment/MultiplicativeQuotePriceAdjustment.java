package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class MultiplicativeQuotePriceAdjustment implements QuotePriceAdjustment {
  private final double factor;

  public MultiplicativeQuotePriceAdjustment(double factor) {
    this.factor = factor;
  }

  @Override
  public Money apply(Money price) {
    return price.multiply(factor);
  }
}
