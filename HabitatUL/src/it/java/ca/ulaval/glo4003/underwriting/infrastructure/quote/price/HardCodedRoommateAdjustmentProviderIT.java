package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.RoommateAdjustmentProviderIT;

public class HardCodedRoommateAdjustmentProviderIT extends RoommateAdjustmentProviderIT {
  @Override
  public RoommateAdjustmentProvider createSubject() {
    return new HardCodedRoommateAdjustmentProvider();
  }
}
