package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.animals;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MaximumPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MinimumPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class HardCodedAnimalsAdjustmentLimitsProvider implements AnimalsAdjustmentLimitsProvider {
  static final float MINIMUM_ADJUSTMENT_FACTOR = -0.1f;
  static final float MAXIMUM_ADJUSTMENT_FACTOR = 1f;

  @Override
  public PremiumAdjustment getMinimumAdjustment(Money basePremium) {
    return new MinimumPremiumAdjustment(computeAdjustment(basePremium, MINIMUM_ADJUSTMENT_FACTOR));
  }

  @Override
  public PremiumAdjustment getMaximumAdjustment(Money basePremium) {
    return new MaximumPremiumAdjustment(computeAdjustment(basePremium, MAXIMUM_ADJUSTMENT_FACTOR));
  }

  private Money computeAdjustment(Money basePremium, float factor) {
    return basePremium.multiply(factor);
  }
}
