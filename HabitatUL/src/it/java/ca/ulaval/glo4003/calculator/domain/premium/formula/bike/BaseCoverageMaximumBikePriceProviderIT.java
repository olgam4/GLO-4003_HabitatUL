package ca.ulaval.glo4003.calculator.domain.premium.formula.bike;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class BaseCoverageMaximumBikePriceProviderIT {
  private BaseCoverageMaximumBikePriceProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingMaximumBikePrice_shouldProvideMaximumBikePrice() {
    Amount maximumBikePrice = subject.getMaximumBikePrice();

    assertNotNull(maximumBikePrice);
  }

  public abstract BaseCoverageMaximumBikePriceProvider createSubject();
}
