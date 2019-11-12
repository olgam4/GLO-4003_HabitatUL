package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bike;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bike.HardCodedBasicBlockCoverageMaximumBikePriceProvider.MAXIMUM_BIKE_PRICE;
import static org.junit.Assert.assertEquals;

public class HardCodedBasicBlockCoverageMaximumBikePriceProviderTest {
  private HardCodedBasicBlockCoverageMaximumBikePriceProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedBasicBlockCoverageMaximumBikePriceProvider();
  }

  @Test
  public void gettingMaximumBikePrice_shouldReturnHardCodedMaximumBikePrice() {
    Amount maximumBikePrice = subject.getMaximumBikePrice();

    assertEquals(MAXIMUM_BIKE_PRICE, maximumBikePrice);
  }
}
