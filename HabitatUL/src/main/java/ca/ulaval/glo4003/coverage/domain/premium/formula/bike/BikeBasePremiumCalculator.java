package ca.ulaval.glo4003.coverage.domain.premium.formula.bike;

import ca.ulaval.glo4003.coverage.domain.premium.formula.BasePremiumCalculator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface BikeBasePremiumCalculator extends BasePremiumCalculator<BikePremiumInput> {
  Money compute(BikePremiumInput bikePremiumInput);
}
