package ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.QuotePremiumFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.input.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class CivilLiabilityLimitFormulaPart implements QuotePremiumFormulaPart {
  private CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider;

  public CivilLiabilityLimitFormulaPart(
      CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider) {
    this.civilLiabilityLimitAdjustmentProvider = civilLiabilityLimitAdjustmentProvider;
  }

  @Override
  public Money compute(QuotePremiumInput quotePremiumInput, Money basePremium) {
    CivilLiabilityLimitInput civilLiabilityLimit = quotePremiumInput.getCivilLiabilityLimit();
    PremiumAdjustment adjustment =
        civilLiabilityLimitAdjustmentProvider.getAdjustment(civilLiabilityLimit);
    return adjustment.apply(basePremium);
  }
}
