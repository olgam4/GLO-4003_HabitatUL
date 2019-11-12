package ca.ulaval.glo4003.coverage.domain.premium.formula.bike;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaPart;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface BikePremiumFormulaPart extends PremiumFormulaPart<BikePremiumInput> {
  Money compute(BikePremiumInput bikePremiumInput, Money basePremium);
}
