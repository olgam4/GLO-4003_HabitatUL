package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class AnimalsAdjustmentLimitsProviderIT {
  private static final Money BASE_PRICE = MoneyGenerator.createMoney();

  private AnimalsAdjustmentLimitsProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideMinimumAdjustment() {
    QuotePriceAdjustment adjustment = subject.getMinimumAdjustment(BASE_PRICE);

    assertNotNull(adjustment);
  }

  @Test
  public void gettingAdjustment_shouldProvideMaximumAdjustment() {
    QuotePriceAdjustment adjustment = subject.getMaximumAdjustment(BASE_PRICE);

    assertNotNull(adjustment);
  }

  public abstract AnimalsAdjustmentLimitsProvider createSubject();
}
