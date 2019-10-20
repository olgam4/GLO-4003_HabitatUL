package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProviderIT;

public class HardCodedAnimalsAdjustmentProviderIT extends AnimalsAdjustmentProviderIT {
  @Override
  public AnimalsAdjustmentProvider createSubject() {
    return new HardCodedAnimalsAdjustmentProvider();
  }
}
