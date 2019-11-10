package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BaseCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class DummyBaseCoverageMaximumBikePriceProvider
    implements BaseCoverageMaximumBikePriceProvider {
  @Override
  public Amount getMaximumBikePrice() {
    return MoneyGenerator.createAmount();
  }
}
