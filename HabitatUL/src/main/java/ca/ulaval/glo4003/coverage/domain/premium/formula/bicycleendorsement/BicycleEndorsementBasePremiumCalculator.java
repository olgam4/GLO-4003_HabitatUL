package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.BasePremiumCalculator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public interface BicycleEndorsementBasePremiumCalculator
    extends BasePremiumCalculator<BicycleEndorsementPremiumInput> {
  Money compute(BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput);
}
