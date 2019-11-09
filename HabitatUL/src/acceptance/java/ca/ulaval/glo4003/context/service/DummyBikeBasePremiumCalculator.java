package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikeBasePremiumCalculator;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BikePremiumInput;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class DummyBikeBasePremiumCalculator implements BikeBasePremiumCalculator {
  @Override
  public Money compute(BikePremiumInput bikePremiumInput) {
    return MoneyGenerator.createMoney();
  }
}
