package ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class BikePriceAdjustmentProviderIT {
  private static final Amount BIKE_PRICE = MoneyGenerator.createAmount();

  private BikePriceAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(BIKE_PRICE);

    assertNotNull(adjustment);
  }

  public abstract BikePriceAdjustmentProvider createSubject();
}
