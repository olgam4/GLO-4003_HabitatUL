package ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface AnimalsAdjustmentLimitsProvider {
  PremiumAdjustment getMinimumAdjustment(Money basePremium);

  PremiumAdjustment getMaximumAdjustment(Money basePremium);
}
