package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createMoney;

public class DummyBicycleEndorsementBasePremiumCalculator
    implements BicycleEndorsementBasePremiumCalculator {
  @Override
  public Money compute(BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput) {
    return createMoney();
  }
}
