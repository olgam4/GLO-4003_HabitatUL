package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class HardCodedBicycleEndorsementBasePremiumCalculator
    implements BicycleEndorsementBasePremiumCalculator {
  static final Money HARDCODED_PREMIUM = new Money(Amount.ZERO);

  @Override
  public Money compute(BicycleEndorsementPremiumInput quotePremiumInput) {
    return HARDCODED_PREMIUM;
  }
}
