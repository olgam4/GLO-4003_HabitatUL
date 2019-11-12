package ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;
import static org.junit.Assert.assertNotNull;

public abstract class CivilLiabilityLimitAdjustmentProviderIT {
  private static final CivilLiabilityLimit CIVIL_LIABILITY_LIMIT = createCivilLiabilityLimit();

  private CivilLiabilityLimitAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(CIVIL_LIABILITY_LIMIT);

    assertNotNull(adjustment);
  }

  public abstract CivilLiabilityLimitAdjustmentProvider createSubject();
}
