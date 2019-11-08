package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HardCodedGraduateStudentAdjustmentProviderTest {
  private HardCodedGraduateStudentAdjustmentProvider subject;
  private String cycle;
  private PremiumAdjustment expectedAdjustment;

  public HardCodedGraduateStudentAdjustmentProviderTest(
      String title, String cycle, PremiumAdjustment expectedAdjustment) {
    this.cycle = cycle;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {"with first cycle should compute null adjustment", "1er", new NullPremiumAdjustment()},
          {
            "with second cycle should compute associated adjustment",
            "2e",
            new MultiplicativePremiumAdjustment(-0.1273f)
          },
          {
            "with third cycle should compute associated adjustment",
            "3e",
            new MultiplicativePremiumAdjustment(-0.1273f)
          },
          {
            "with any other cycle should compute null adjustment",
            "anything else",
            new NullPremiumAdjustment()
          },
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
