package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProviderIT;

public class HardCodedCivilLiabilityLimitAdjustmentProviderIT
    extends CivilLiabilityLimitAdjustmentProviderIT {
  @Override
  public CivilLiabilityLimitAdjustmentProvider createSubject() {
    return new HardCodedCivilLiabilityLimitAdjustmentProvider();
  }
}
