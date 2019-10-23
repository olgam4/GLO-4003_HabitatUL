package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HardCodedCivilLiabilityLimitAdjustmentProviderTest {
  private HardCodedCivilLiabilityLimitAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedCivilLiabilityLimitAdjustmentProvider();
  }

  @Test
  public void
      gettingAdjustment_withKnownCivilLiabilityLimit_shouldProvideCorrespondingAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(CivilLiabilityLimit.TWO_MILLION);

    QuotePriceAdjustment expectedAdjustment = new MultiplicativeQuotePriceAdjustment(0.25f);
    assertEquals(expectedAdjustment, adjustment);
  }

  @Test
  public void gettingAdjustment_withUnknownCivilLiabilityLimit_shouldProvideNoAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(CivilLiabilityLimit.ONE_MILLION);

    QuotePriceAdjustment expectedAdjustment = new NoQuotePriceAdjustment();
    assertEquals(expectedAdjustment, adjustment);
  }
}
