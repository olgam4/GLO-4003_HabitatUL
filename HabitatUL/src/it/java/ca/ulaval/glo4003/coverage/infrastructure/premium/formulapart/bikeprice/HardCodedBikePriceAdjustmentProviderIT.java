package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.bikeprice;

import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProviderIT;

public class HardCodedBikePriceAdjustmentProviderIT extends BikePriceAdjustmentProviderIT {
  @Override
  public BikePriceAdjustmentProvider createSubject() {
    return new HardCodedBikePriceAdjustmentProvider();
  }
}
