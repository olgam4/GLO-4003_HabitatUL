package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BasicBlockCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class DummyBasicBlockCoverageMaximumBikePriceProvider
    implements BasicBlockCoverageMaximumBikePriceProvider {
  @Override
  public Amount getMaximumBikePrice() {
    return MoneyGenerator.createAmount();
  }
}
