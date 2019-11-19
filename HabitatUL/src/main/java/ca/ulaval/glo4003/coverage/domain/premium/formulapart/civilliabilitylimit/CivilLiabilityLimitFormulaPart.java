package ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class CivilLiabilityLimitFormulaPart {
  CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider;

  public CivilLiabilityLimitFormulaPart(
      CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider) {
    this.civilLiabilityLimitAdjustmentProvider = civilLiabilityLimitAdjustmentProvider;
  }

  Money compute(Money basePremium, CivilLiabilityLimit civilLiabilityLimit) {
    PremiumAdjustment adjustment =
        civilLiabilityLimitAdjustmentProvider.getAdjustment(civilLiabilityLimit);
    return adjustment.apply(basePremium);
  }
}
