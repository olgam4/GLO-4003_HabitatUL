package ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormulaPart;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class CivilLiabilityLimitQuoteBasicBlockPremiumFormulaPart
    extends CivilLiabilityLimitFormulaPart implements QuoteBasicBlockPremiumFormulaPart {
  public CivilLiabilityLimitQuoteBasicBlockPremiumFormulaPart(
      CivilLiabilityLimitAdjustmentProvider civilLiabilityLimitAdjustmentProvider) {
    super(civilLiabilityLimitAdjustmentProvider);
  }

  @Override
  public Money compute(QuotePremiumInput quotePremiumInput, Money basePremium) {
    return compute(basePremium, quotePremiumInput.getCivilLiabilityLimit());
  }
}
