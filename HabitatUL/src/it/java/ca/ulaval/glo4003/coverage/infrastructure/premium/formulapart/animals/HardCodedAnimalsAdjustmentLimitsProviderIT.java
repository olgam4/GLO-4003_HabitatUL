package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.animals;

import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentLimitsProviderIT;

public class HardCodedAnimalsAdjustmentLimitsProviderIT extends AnimalsAdjustmentLimitsProviderIT {
  @Override
  public AnimalsAdjustmentLimitsProvider createSubject() {
    return new HardCodedAnimalsAdjustmentLimitsProvider();
  }
}
