package ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.helper.form.identity.IdentityGenerator.createGender;
import static org.junit.Assert.assertNotNull;

public abstract class RoommateAdjustmentProviderIT {
  private static final Gender NAMED_INSURED_GENDER = createGender();
  private static final Gender ROOMMATE_GENDER = createGender();

  private RoommateAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(NAMED_INSURED_GENDER, ROOMMATE_GENDER);

    assertNotNull(adjustment);
  }

  public abstract RoommateAdjustmentProvider createSubject();
}
