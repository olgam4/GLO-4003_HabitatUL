package ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProgramGenerator.createCycle;
import static org.junit.Assert.assertNotNull;

public abstract class GraduateStudentAdjustmentProviderIT {
  private static final Cycle CYCLE = createCycle();

  private GraduateStudentAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(CYCLE);

    assertNotNull(adjustment);
  }

  public abstract GraduateStudentAdjustmentProvider createSubject();
}
