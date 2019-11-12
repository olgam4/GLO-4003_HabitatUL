package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class DummyBikePriceAdjustmentProvider implements BikePriceAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(Amount bikePrice) {
    return PremiumAdjustmentGenerator.create();
  }
}
