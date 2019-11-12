package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class DummyBicycleEndorsementBasePremiumCalculator
    implements BicycleEndorsementBasePremiumCalculator {
  @Override
  public Money compute(BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput) {
    return MoneyGenerator.createMoney();
  }
}
