package ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BasicBlockCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BasicBlockCoverageMaximumBikePriceProviderIT;

public class HardCodedBasicBlockCoverageMaximumBikePriceProviderIT
    extends BasicBlockCoverageMaximumBikePriceProviderIT {
  @Override
  public BasicBlockCoverageMaximumBikePriceProvider createSubject() {
    return new HardCodedBasicBlockCoverageMaximumBikePriceProvider();
  }
}
