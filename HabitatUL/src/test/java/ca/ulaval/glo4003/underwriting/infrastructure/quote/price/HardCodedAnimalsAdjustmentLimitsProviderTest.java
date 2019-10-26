package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class HardCodedAnimalsAdjustmentLimitsProviderTest {
  private HardCodedAnimaAdjustmentLimitsProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedAnimaAdjustmentLimitsProvider();
  }

  @Test
  public void gettingMinAdjustment_shouldProvideCorrespondingAdjustment() {
    QuotePriceAdjustment actualMin =  subject.getMin();

    assertEquals(HardCodedAnimaAdjustmentLimitsProvider.MINIMUM_ADJUSTMENT, actualMin);
  }

  @Test
  public void gettingMaxAdjustment_shouldProvideCorrespondingAdjustment() {
    QuotePriceAdjustment actualMax =  subject.getMax();

    assertEquals(HardCodedAnimaAdjustmentLimitsProvider.MAXIMUM_ADJUSTMENT, actualMax);
  }
}
