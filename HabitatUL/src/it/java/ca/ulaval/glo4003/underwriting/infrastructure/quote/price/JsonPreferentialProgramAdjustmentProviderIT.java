package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProviderIT;

public class JsonPreferentialProgramAdjustmentProviderIT
    extends PreferentialProgramAdjustmentProviderIT {
  @Override
  public PreferentialProgramAdjustmentProvider createSubject() {
    return new JsonPreferentialProgramAdjustmentProvider();
  }
}
