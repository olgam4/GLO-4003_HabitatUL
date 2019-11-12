package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicyclePriceAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class DummyBicyclePriceAdjustmentProvider implements BicyclePriceAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(Amount bicyclePrice) {
    return PremiumAdjustmentGenerator.create();
  }
}
