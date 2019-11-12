package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.PremiumFormulaPart;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface BicycleEndorsementPremiumFormulaPart
    extends PremiumFormulaPart<BicycleEndorsementPremiumInput> {
  Money compute(BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput, Money basePremium);
}
