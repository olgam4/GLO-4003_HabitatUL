package ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.helper.MoneyGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class BicyclePriceAdjustmentProviderIT {
  private static final Amount BICYCLE_PRICE = MoneyGenerator.createAmount();

  private BicyclePriceAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(BICYCLE_PRICE);

    assertNotNull(adjustment);
  }

  public abstract BicyclePriceAdjustmentProvider createSubject();
}
