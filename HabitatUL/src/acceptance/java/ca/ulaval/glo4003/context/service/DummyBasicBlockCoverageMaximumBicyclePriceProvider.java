package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BasicBlockCoverageMaximumBicyclePriceProvider;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;

public class DummyBasicBlockCoverageMaximumBicyclePriceProvider
    implements BasicBlockCoverageMaximumBicyclePriceProvider {
  @Override
  public Amount getMaximumBicyclePrice() {
    return MoneyGenerator.createAmount();
  }
}
