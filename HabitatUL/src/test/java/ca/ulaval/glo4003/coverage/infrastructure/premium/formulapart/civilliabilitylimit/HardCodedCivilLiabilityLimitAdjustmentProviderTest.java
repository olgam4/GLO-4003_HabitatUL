package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.shared.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HardCodedCivilLiabilityLimitAdjustmentProviderTest {
  private HardCodedCivilLiabilityLimitAdjustmentProvider subject;
  private CivilLiabilityLimit civilLiabilityLimit;
  private PremiumAdjustment expectedAdjustment;

  public HardCodedCivilLiabilityLimitAdjustmentProviderTest(
      String title, CivilLiabilityLimit civilLiabilityLimit, PremiumAdjustment expectedAdjustment) {
    this.civilLiabilityLimit = civilLiabilityLimit;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "without civil liability limit should compute null adjustment",
            null,
            new NullPremiumAdjustment()
          },
          {
            "with 1M civil liability limit should compute null adjustment",
            CivilLiabilityLimit.ONE_MILLION,
            new NullPremiumAdjustment()
          },
          {
            "with 2M civil liability limit should compute associated adjustment",
            CivilLiabilityLimit.TWO_MILLION,
            new MultiplicativePremiumAdjustment(0.25f)
          },
        });
  }

  @Before
  public void setUp() {
    subject = new HardCodedCivilLiabilityLimitAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(civilLiabilityLimit);

    assertEquals(expectedAdjustment, adjustment);
  }
}
