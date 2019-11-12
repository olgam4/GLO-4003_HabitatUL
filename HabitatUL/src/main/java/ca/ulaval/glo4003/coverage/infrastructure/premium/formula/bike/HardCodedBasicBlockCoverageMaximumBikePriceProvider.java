package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BasicBlockCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;

public class HardCodedBasicBlockCoverageMaximumBikePriceProvider
    implements BasicBlockCoverageMaximumBikePriceProvider {
  static final Amount MAXIMUM_BIKE_PRICE = new Amount(BigDecimal.valueOf(1500));

  @Override
  public Amount getMaximumBikePrice() {
    return MAXIMUM_BIKE_PRICE;
  }
}
