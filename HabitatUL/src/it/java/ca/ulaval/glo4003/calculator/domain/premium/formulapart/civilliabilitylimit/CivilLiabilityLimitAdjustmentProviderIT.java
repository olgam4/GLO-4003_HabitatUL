package ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.input.CivilLiabilityLimitInput;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator.createCivilLiabilityLimitInput;
import static org.junit.Assert.assertNotNull;

public abstract class CivilLiabilityLimitAdjustmentProviderIT {
  private static final CivilLiabilityLimitInput CIVIL_LIABILITY_LIMIT =
      createCivilLiabilityLimitInput();

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
