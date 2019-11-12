package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.animals;

import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProviderIT;

public class HardCodedAnimalsAdjustmentProviderIT extends AnimalsAdjustmentProviderIT {
  @Override
  public AnimalsAdjustmentProvider createSubject() {
    return new HardCodedAnimalsAdjustmentProvider();
  }
}
