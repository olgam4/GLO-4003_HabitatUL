package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class AnimalsAdjustmentLimitsProviderIT {
  private AnimalsAdjustmentLimitsProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideMinAdjustment() {
    QuotePriceAdjustment adjustment = subject.getMin();

    assertNotNull(adjustment);
  }

  @Test
  public void gettingAdjustment_shouldProvideMaxAdjustment() {
    QuotePriceAdjustment adjustment = subject.getMax();

    assertNotNull(adjustment);
  }

  public abstract AnimalsAdjustmentLimitsProvider createSubject();
}
