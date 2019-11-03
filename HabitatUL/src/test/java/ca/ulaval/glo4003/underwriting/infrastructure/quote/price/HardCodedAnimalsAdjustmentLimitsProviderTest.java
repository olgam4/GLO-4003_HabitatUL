package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MaximumQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MinimumQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.underwriting.infrastructure.quote.price.HardCodedAnimalsAdjustmentLimitsProvider.MAXIMUM_ADJUSTMENT_FACTOR;
import static ca.ulaval.glo4003.underwriting.infrastructure.quote.price.HardCodedAnimalsAdjustmentLimitsProvider.MINIMUM_ADJUSTMENT_FACTOR;
import static org.junit.Assert.assertEquals;

public class HardCodedAnimalsAdjustmentLimitsProviderTest {
  private static final Money BASE_PRICE = MoneyGenerator.createMoney();

  private HardCodedAnimalsAdjustmentLimitsProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedAnimalsAdjustmentLimitsProvider();
  }

  @Test
  public void gettingMinimumAdjustment_shouldProvideCorrespondingAdjustment() {
    QuotePriceAdjustment minimumAdjustment = subject.getMinimumAdjustment(BASE_PRICE);

    MinimumQuotePriceAdjustment expectedAdjustment =
        new MinimumQuotePriceAdjustment(BASE_PRICE.multiply(MINIMUM_ADJUSTMENT_FACTOR));
    assertEquals(expectedAdjustment, minimumAdjustment);
  }

  @Test
  public void gettingMaximumAdjustment_shouldProvideCorrespondingAdjustment() {
    QuotePriceAdjustment maximumAdjustment = subject.getMaximumAdjustment(BASE_PRICE);

    MaximumQuotePriceAdjustment expectedAdjustment =
        new MaximumQuotePriceAdjustment(BASE_PRICE.multiply(MAXIMUM_ADJUSTMENT_FACTOR));
    assertEquals(expectedAdjustment, maximumAdjustment);
  }
}
