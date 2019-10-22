package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class RoommateAdjustmentProviderIT {
  private static final Gender GENDER = EnumSampler.sample(Gender.class);
  private static final Gender ROOMMATE_GENDER = EnumSampler.sample(Gender.class);

  private RoommateAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(GENDER, ROOMMATE_GENDER);

    assertNotNull(adjustment);
  }

  public abstract RoommateAdjustmentProvider createSubject();
}
