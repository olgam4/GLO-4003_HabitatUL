package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.roommate;

import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProviderIT;

public class HardCodedRoommateAdjustmentProviderIT extends RoommateAdjustmentProviderIT {
  @Override
  public RoommateAdjustmentProvider createSubject() {
    return new HardCodedRoommateAdjustmentProvider();
  }
}
