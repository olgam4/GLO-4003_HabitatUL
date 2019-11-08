package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class DummyAnimalsAdjustmentLimitsProvider implements AnimalsAdjustmentLimitsProvider {
  @Override
  public PremiumAdjustment getMinimumAdjustment(Money basePremium) {
    return PremiumAdjustmentGenerator.create();
  }

  @Override
  public PremiumAdjustment getMaximumAdjustment(Money basePremium) {
    return PremiumAdjustmentGenerator.create();
  }
}
