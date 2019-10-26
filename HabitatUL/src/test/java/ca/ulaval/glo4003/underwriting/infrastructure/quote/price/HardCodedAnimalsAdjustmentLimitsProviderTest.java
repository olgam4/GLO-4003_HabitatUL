package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HardCodedAnimalsAdjustmentLimitsProviderTest {
  private HardCodedAnimalsAdjustmentLimitsProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedAnimalsAdjustmentLimitsProvider();
  }

  @Test
  public void gettingMinAdjustment_shouldProvideCorrespondingAdjustment() {
    QuotePriceAdjustment actualMin = subject.getMin();

    assertEquals(HardCodedAnimalsAdjustmentLimitsProvider.MINIMUM_ADJUSTMENT, actualMin);
  }

  @Test
  public void gettingMaxAdjustment_shouldProvideCorrespondingAdjustment() {
    QuotePriceAdjustment actualMax = subject.getMax();

    assertEquals(HardCodedAnimalsAdjustmentLimitsProvider.MAXIMUM_ADJUSTMENT, actualMax);
  }
}
