package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProviderIT;

public class JsonPreferentialProgramAdjustmentProviderIT
    extends PreferentialProgramAdjustmentProviderIT {
  @Override
  public PreferentialProgramAdjustmentProvider createSubject() {
    return new JsonPreferentialProgramAdjustmentProvider();
  }
}
