package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class NoQuotePriceAdjustment implements QuotePriceAdjustment {
  @Override
  public Money apply(Money price) {
    return new Money(Amount.ZERO);
  }
}
