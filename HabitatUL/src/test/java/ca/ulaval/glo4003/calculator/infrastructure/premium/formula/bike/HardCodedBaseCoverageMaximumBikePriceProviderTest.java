package ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.calculator.infrastructure.premium.formula.bike.HardCodedBaseCoverageMaximumBikePriceProvider.MAXIMUM_BIKE_PRICE;
import static org.junit.Assert.assertEquals;

public class HardCodedBaseCoverageMaximumBikePriceProviderTest {
  private HardCodedBaseCoverageMaximumBikePriceProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedBaseCoverageMaximumBikePriceProvider();
  }

  @Test
  public void gettingMaximumBikePrice_shouldReturnHardCodedMaximumBikePrice() {
    Amount maximumBikePrice = subject.getMaximumBikePrice();

    assertEquals(MAXIMUM_BIKE_PRICE, maximumBikePrice);
  }
}
