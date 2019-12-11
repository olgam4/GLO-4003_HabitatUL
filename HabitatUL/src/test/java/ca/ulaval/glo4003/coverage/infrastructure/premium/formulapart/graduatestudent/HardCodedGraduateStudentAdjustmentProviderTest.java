package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static ca.ulaval.glo4003.shared.domain.identity.Cycle.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HardCodedGraduateStudentAdjustmentProviderTest {
  private HardCodedGraduateStudentAdjustmentProvider subject;
  private Cycle cycle;
  private PremiumAdjustment expectedAdjustment;

  public HardCodedGraduateStudentAdjustmentProviderTest(
      String title, Cycle cycle, PremiumAdjustment expectedAdjustment) {
    this.cycle = cycle;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {"without cycle should compute null adjustment", null, new NullPremiumAdjustment()},
          {
            "with first cycle should compute null adjustment",
            FIRST_CYCLE,
            new NullPremiumAdjustment()
          },
          {
            "with second cycle should compute associated adjustment",
            SECOND_CYCLE,
            new MultiplicativePremiumAdjustment(-0.1273f)
          },
          {
            "with third cycle should compute associated adjustment",
            THIRD_CYCLE,
            new MultiplicativePremiumAdjustment(-0.1273f)
          }
        });
  }

  @Before
  public void setUp() {
    subject = new HardCodedGraduateStudentAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(cycle);

    assertEquals(expectedAdjustment, adjustment);
  }
}
