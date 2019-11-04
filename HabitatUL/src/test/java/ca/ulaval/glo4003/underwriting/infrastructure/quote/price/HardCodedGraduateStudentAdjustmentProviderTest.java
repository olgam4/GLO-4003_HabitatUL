package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NullQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
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
  private QuotePriceAdjustment expectedAdjustment;

  public HardCodedGraduateStudentAdjustmentProviderTest(
      String title, String cycle, QuotePriceAdjustment expectedAdjustment) {
    this.cycle = cycle;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "with first cycle should compute null adjustment", "1er", new NullQuotePriceAdjustment()
          },
          {
            "with second cycle should compute associated adjustment",
            "2e",
            new MultiplicativeQuotePriceAdjustment(-0.1273f)
          },
          {
            "with third cycle should compute associated adjustment",
            "3e",
            new MultiplicativeQuotePriceAdjustment(-0.1273f)
          },
          {
            "with any other cycle should compute no adjustment",
            "anything else",
            new NullQuotePriceAdjustment()
          },
        });
  }

  @Before
  public void setUp() {
    subject = new HardCodedGraduateStudentAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(cycle);

    assertEquals(expectedAdjustment, adjustment);
  }
}
