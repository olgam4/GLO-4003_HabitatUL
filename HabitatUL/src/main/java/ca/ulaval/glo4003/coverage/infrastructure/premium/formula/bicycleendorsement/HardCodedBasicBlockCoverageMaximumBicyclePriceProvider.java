package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BasicBlockCoverageMaximumBicyclePriceProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;

public class HardCodedBasicBlockCoverageMaximumBicyclePriceProvider
    implements BasicBlockCoverageMaximumBicyclePriceProvider {
  static final Amount MAXIMUM_BICYCLE_PRICE = new Amount(BigDecimal.valueOf(1500));

  @Override
  public Amount getMaximumBicyclePrice() {
    return MAXIMUM_BICYCLE_PRICE;
  }
}
