package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.roommate;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.shared.domain.identity.Gender.*;
import static ca.ulaval.glo4003.shared.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class HardCodedRoommateAdjustmentProviderTest {
  private final Gender namedInsuredGender;
  private final Gender roommateGender;
  private final PremiumAdjustment expectedAdjustment;
  private HardCodedRoommateAdjustmentProvider subject;

  public HardCodedRoommateAdjustmentProviderTest(
      String title,
      Gender namedInsuredGender,
      Gender roommateGender,
      PremiumAdjustment expectedAdjustment) {
    this.namedInsuredGender = namedInsuredGender;
    this.roommateGender = roommateGender;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "without roommate should compute null adjustment",
            MALE,
            null,
            new NullPremiumAdjustment()
          },
          {
            "with male named insured and male roommate should compute associated adjustment",
            MALE,
            MALE,
            new MultiplicativePremiumAdjustment(0.1f)
          },
          {
            "with male named insured and female roommate should compute null adjustment",
            MALE,
            FEMALE,
            new NullPremiumAdjustment()
          },
          {
            "with male named insured and other roommate should compute null adjustment",
            MALE,
            OTHER,
            new NullPremiumAdjustment()
          },
          {
            "with female named insured and male roommate should compute null adjustment",
            FEMALE,
            MALE,
            new NullPremiumAdjustment()
          },
          {
            "with female named insured and female roommate should compute null adjustment",
            FEMALE,
            FEMALE,
            new NullPremiumAdjustment()
          },
          {
            "with female named insured and other roommate should compute null adjustment",
            FEMALE,
            OTHER,
            new NullPremiumAdjustment()
          },
          {
            "with other named insured and male roommate should compute null adjustment",
            OTHER,
            MALE,
            new NullPremiumAdjustment()
          },
          {
            "with other named insured and female roommate should compute null adjustment",
            OTHER,
            FEMALE,
            new NullPremiumAdjustment()
          },
          {
            "with other named insured and other roommate should compute null adjustment",
            OTHER,
            OTHER,
            new NullPremiumAdjustment()
          },
        });
  }

  @Before
  public void setUp() {
    subject = new HardCodedRoommateAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(namedInsuredGender, roommateGender);

    assertEquals(expectedAdjustment, adjustment);
  }
}
