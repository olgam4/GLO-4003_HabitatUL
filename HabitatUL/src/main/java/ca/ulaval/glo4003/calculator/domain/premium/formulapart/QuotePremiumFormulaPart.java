package ca.ulaval.glo4003.calculator.domain.premium.formulapart;

import ca.ulaval.glo4003.calculator.domain.premium.input.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface QuotePremiumFormulaPart {
  Money compute(QuotePremiumInput quotePremiumInput, Money basePremium);
}
