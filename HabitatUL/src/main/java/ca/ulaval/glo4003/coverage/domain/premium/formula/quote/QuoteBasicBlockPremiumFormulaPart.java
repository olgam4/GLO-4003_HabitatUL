package ca.ulaval.glo4003.coverage.domain.premium.formula.quote;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaPart;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface QuoteBasicBlockPremiumFormulaPart extends PremiumFormulaPart<QuotePremiumInput> {
  Money compute(QuotePremiumInput quotePremiumInput, Money basePremium);
}
