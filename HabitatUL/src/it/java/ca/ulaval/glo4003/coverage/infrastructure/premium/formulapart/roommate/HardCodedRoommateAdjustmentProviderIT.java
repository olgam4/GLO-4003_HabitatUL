package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.roommate;

import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateAdjustmentProviderIT;

public class HardCodedRoommateAdjustmentProviderIT extends RoommateAdjustmentProviderIT {
  @Override
  public RoommateAdjustmentProvider createSubject() {
    return new HardCodedRoommateAdjustmentProvider();
  }
}
