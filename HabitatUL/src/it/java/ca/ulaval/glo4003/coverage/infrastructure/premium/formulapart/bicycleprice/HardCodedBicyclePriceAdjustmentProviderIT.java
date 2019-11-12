package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.bicycleprice;

import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicyclePriceAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicyclePriceAdjustmentProviderIT;

public class HardCodedBicyclePriceAdjustmentProviderIT extends BicyclePriceAdjustmentProviderIT {
  @Override
  public BicyclePriceAdjustmentProvider createSubject() {
    return new HardCodedBicyclePriceAdjustmentProvider();
  }
}
