package ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public abstract class PreferentialProgramAdjustmentProviderIT {
  private static final String CYCLE = "cycle";
  private static final String DEGREE = "degree";
  private static final String PROGRAM = "program";

  private PreferentialProgramAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(CYCLE, DEGREE, PROGRAM);

    assertNotNull(adjustment);
  }

  public abstract PreferentialProgramAdjustmentProvider createSubject();
}
