package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class CivilLiabilityLimitAdjustmentProviderIT {
  private static final CivilLiabilityLimit CIVIL_LIABILITY_LIMIT =
      EnumSampler.sample(CivilLiabilityLimit.class);

  private CivilLiabilityLimitAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(CIVIL_LIABILITY_LIMIT);

    assertNotNull(adjustment);
  }

  public abstract CivilLiabilityLimitAdjustmentProvider createSubject();
}
