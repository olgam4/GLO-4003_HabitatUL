package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.infrastructure.premium.formula.bicycleendorsement.HardCodedBasicBlockCoverageMaximumBicyclePriceProvider.MAXIMUM_BICYCLE_PRICE;
import static org.junit.Assert.assertEquals;

public class HardCodedBasicBlockCoverageMaximumBicyclePriceProviderTest {
  private HardCodedBasicBlockCoverageMaximumBicyclePriceProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedBasicBlockCoverageMaximumBicyclePriceProvider();
  }

  @Test
  public void gettingMaximumBicyclePrice_shouldReturnHardCodedMaximumBicyclePrice() {
    Amount maximumBicyclePrice = subject.getMaximumBicyclePrice();

    assertEquals(MAXIMUM_BICYCLE_PRICE, maximumBicyclePrice);
  }
}
