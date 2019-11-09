package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.bikeprice;

import ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProviderIT;

public class HardCodedBikePriceAdjustmentProviderIT extends BikePriceAdjustmentProviderIT {
  @Override
  public BikePriceAdjustmentProvider createSubject() {
    return new HardCodedBikePriceAdjustmentProvider();
  }
}
