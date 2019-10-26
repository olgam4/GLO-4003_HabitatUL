package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProviderIT;

public class HardCodedAnimalsAdjustmentLimitsProviderIT extends AnimalsAdjustmentLimitsProviderIT{

  @Override
  public AnimalsAdjustmentLimitsProvider createSubject() {
    return new HardCodedAnimaAdjustmentLimitsProvider();
  }
}
