package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProviderIT;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

public class JsonPreferentialProgramAdjustmentProviderIT
    extends PreferentialProgramAdjustmentProviderIT {
  @Override
  public PreferentialProgramAdjustmentProvider createSubject() {
    try {
      return new JsonPreferentialProgramAdjustmentProvider();
    } catch (InvalidArgumentException e) {
      return null;
    }
  }
}
