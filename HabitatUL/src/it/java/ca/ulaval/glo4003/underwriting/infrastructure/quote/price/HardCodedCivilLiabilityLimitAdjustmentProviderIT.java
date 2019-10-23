package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.CivilLiabilityLimitAdjustmentProviderIT;

public class HardCodedCivilLiabilityLimitAdjustmentProviderIT
    extends CivilLiabilityLimitAdjustmentProviderIT {
  @Override
  public CivilLiabilityLimitAdjustmentProvider createSubject() {
    return new HardCodedCivilLiabilityLimitAdjustmentProvider();
  }
}
