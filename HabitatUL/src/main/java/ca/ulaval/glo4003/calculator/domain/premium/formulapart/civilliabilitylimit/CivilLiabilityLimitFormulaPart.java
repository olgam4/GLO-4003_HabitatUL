package ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.input.CivilLiabilityLimit;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumFormulaPart;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class CivilLiabilityLimitFormulaPart implements QuotePremiumFormulaPart {
  private CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider;

  public CivilLiabilityLimitFormulaPart(
      CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider) {
    this.civilLiabilityLimitAdjustmentProvider = civilLiabilityLimitAdjustmentProvider;
  }

  @Override
  public Money compute(QuotePremiumInput quotePremiumInput, Money basePremium) {
    CivilLiabilityLimit civilLiabilityLimit = quotePremiumInput.getCivilLiabilityLimit();
    PremiumAdjustment adjustment =
        civilLiabilityLimitAdjustmentProvider.getAdjustment(civilLiabilityLimit);
    return adjustment.apply(basePremium);
  }
}
