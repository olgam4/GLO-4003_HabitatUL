package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BasicBlockCoverageMaximumBicyclePriceProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BasicBlockCoverageMaximumBicyclePriceProviderIT;

public class HardCodedBasicBlockCoverageMaximumBicyclePriceProviderIT
    extends BasicBlockCoverageMaximumBicyclePriceProviderIT {
  @Override
  public BasicBlockCoverageMaximumBicyclePriceProvider createSubject() {
    return new HardCodedBasicBlockCoverageMaximumBicyclePriceProvider();
  }
}
