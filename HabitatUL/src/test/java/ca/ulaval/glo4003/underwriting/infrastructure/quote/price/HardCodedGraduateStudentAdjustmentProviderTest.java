package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HardCodedGraduateStudentAdjustmentProviderTest {
  private HardCodedGraduateStudentAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedGraduateStudentAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment_withKnownCycle_shouldProvideCorrespondingAdjustment() {
    validateAdjustmentScenario("2e", -0.1273f);
    validateAdjustmentScenario("3e", -0.1273f);
  }

  private void validateAdjustmentScenario(String cycle, float expectedFactor) {
    QuotePriceAdjustment adjustment = subject.getAdjustment(cycle);

    QuotePriceAdjustment expectedAdjustment =
        new MultiplicativeQuotePriceAdjustment(expectedFactor);
    assertEquals(expectedAdjustment, adjustment);
  }
}
