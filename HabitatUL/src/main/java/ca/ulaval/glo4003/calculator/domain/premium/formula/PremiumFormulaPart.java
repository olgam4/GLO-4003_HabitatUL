package ca.ulaval.glo4003.calculator.domain.premium.formula;

import ca.ulaval.glo4003.shared.domain.money.Money;

public interface PremiumFormulaPart<T> {
  Money compute(T premiumInput, Money basePremium);
}
