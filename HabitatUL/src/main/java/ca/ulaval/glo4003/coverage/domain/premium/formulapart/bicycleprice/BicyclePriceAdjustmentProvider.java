package ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public interface BicyclePriceAdjustmentProvider {
  PremiumAdjustment getAdjustment(Amount bicyclePrice);
}
