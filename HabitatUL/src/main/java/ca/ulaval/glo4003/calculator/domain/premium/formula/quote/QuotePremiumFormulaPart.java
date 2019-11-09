package ca.ulaval.glo4003.calculator.domain.premium.formula.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.PremiumFormulaPart;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface QuotePremiumFormulaPart extends PremiumFormulaPart<QuotePremiumInput> {
  Money compute(QuotePremiumInput quotePremiumInput, Money basePremium);
}
