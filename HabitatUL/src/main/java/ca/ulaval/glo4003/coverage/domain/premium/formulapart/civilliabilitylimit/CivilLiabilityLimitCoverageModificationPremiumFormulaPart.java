package ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class CivilLiabilityLimitCoverageModificationPremiumFormulaPart
    extends CivilLiabilityLimitFormulaPart implements CoverageModificationPremiumFormulaPart {
  public CivilLiabilityLimitCoverageModificationPremiumFormulaPart(
      CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider) {
    super(civilLiabilityLimitAdjustmentProvider);
  }

  @Override
  public Money compute(
      CoverageModificationPremiumInput coverageModificationPremiumInput, Money basePremium) {
    return compute(basePremium, coverageModificationPremiumInput.getCivilLiabilityLimit());
  }
}
