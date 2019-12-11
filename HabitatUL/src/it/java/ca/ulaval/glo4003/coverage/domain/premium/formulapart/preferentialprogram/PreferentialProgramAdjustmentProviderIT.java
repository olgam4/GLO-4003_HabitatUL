package ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.shared.domain.identity.Cycle.FIRST_CYCLE;
import static ca.ulaval.glo4003.shared.domain.identity.Degree.BACHELOR;
import static org.junit.Assert.assertNotNull;

public abstract class PreferentialProgramAdjustmentProviderIT {
  private static final Cycle CYCLE = FIRST_CYCLE;
  private static final Degree DEGREE = BACHELOR;
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
