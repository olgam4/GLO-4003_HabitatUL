package ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.calculator.domain.premium.formula.bike.BaseCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;

public class HardCodedBaseCoverageMaximumBikePriceProvider
    implements BaseCoverageMaximumBikePriceProvider {
  static final Amount MAXIMUM_BIKE_PRICE = new Amount(BigDecimal.valueOf(1500));

  @Override
  public Amount getMaximumBikePrice() {
    return MAXIMUM_BIKE_PRICE;
  }
}
