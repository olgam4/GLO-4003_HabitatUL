package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

public abstract class QuotePriceAdjustment extends ValueComparableObject {
  public abstract Money apply(Money price);
}
