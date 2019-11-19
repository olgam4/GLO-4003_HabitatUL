package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.shared.domain.money.Money;

import static ca.ulaval.glo4003.context.PremiumAdjustmentGenerator.createPremiumAdjustment;

public class DummyAnimalsAdjustmentLimitsProvider implements AnimalsAdjustmentLimitsProvider {
  @Override
  public PremiumAdjustment getMinimumAdjustment(Money basePremium) {
    return createPremiumAdjustment();
  }

  @Override
  public PremiumAdjustment getMaximumAdjustment(Money basePremium) {
    return createPremiumAdjustment();
  }
}
