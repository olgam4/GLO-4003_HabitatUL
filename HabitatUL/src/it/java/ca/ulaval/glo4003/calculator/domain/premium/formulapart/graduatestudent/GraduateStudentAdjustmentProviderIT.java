package ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.calculator.form.identity.UniversityProgramGenerator.createCycle;
import static org.junit.Assert.assertNotNull;

public abstract class GraduateStudentAdjustmentProviderIT {
  private static final String CYCLE = createCycle();

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
