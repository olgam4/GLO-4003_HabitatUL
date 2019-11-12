package ca.ulaval.glo4003.coverage.domain.premium.adjustment;

import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

public abstract class PremiumAdjustment extends ValueComparableObject {
  public abstract Money apply(Money premium);
}
