package ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class BasicBlockCoverageMaximumBicyclePriceProviderIT {
  private BasicBlockCoverageMaximumBicyclePriceProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingMaximumBicyclePrice_shouldProvideMaximumBicyclePrice() {
    Amount maximumBicyclePrice = subject.getMaximumBicyclePrice();

    assertNotNull(maximumBicyclePrice);
  }

  public abstract BasicBlockCoverageMaximumBicyclePriceProvider createSubject();
}
