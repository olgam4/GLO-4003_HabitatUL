package ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BaseCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BaseCoverageMaximumBikePriceProviderIT;

public class HardCodedBaseCoverageMaximumBikePriceProviderIT
    extends BaseCoverageMaximumBikePriceProviderIT {
  @Override
  public BaseCoverageMaximumBikePriceProvider createSubject() {
    return new HardCodedBaseCoverageMaximumBikePriceProvider();
  }
}
