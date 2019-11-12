package ca.ulaval.glo4003.coverage.domain.premium.formulapart.bikeprice;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public interface BikePriceAdjustmentProvider {
  PremiumAdjustment getAdjustment(Amount bikePrice);
}
