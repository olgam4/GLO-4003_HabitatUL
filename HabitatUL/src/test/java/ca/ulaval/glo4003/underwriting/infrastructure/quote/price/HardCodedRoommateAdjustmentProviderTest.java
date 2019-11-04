package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
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
public class HardCodedRoommateAdjustmentProviderTest {
  private final Gender namedInsuredGender;
  private final Gender roommateGender;
  private final QuotePriceAdjustment expectedAdjustment;
  private HardCodedRoommateAdjustmentProvider subject;

  public HardCodedRoommateAdjustmentProviderTest(
      String title,
      Gender namedInsuredGender,
      Gender roommateGender,
      QuotePriceAdjustment expectedAdjustment) {

    this.namedInsuredGender = namedInsuredGender;
    this.roommateGender = roommateGender;
    this.expectedAdjustment = expectedAdjustment;
  }

  @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
  public static Collection parameters() {
    return Arrays.asList(
        new Object[][] {
          {
            "with male named insured and male roommate should compute associated adjustment",
            Gender.MALE,
            Gender.MALE,
            new MultiplicativeQuotePriceAdjustment(0.1f)
          },
          {
            "with male named insured and female roommate should compute null adjustment",
            Gender.MALE,
            Gender.FEMALE,
            new NullQuotePriceAdjustment()
          },
          {
            "with male named insured and other roommate should compute null adjustment",
            Gender.MALE,
            Gender.OTHER,
            new NullQuotePriceAdjustment()
          },
          {
            "with female named insured and male roommate should compute null adjustment",
            Gender.FEMALE,
            Gender.MALE,
            new NullQuotePriceAdjustment()
          },
          {
            "with female named insured and female roommate should compute null adjustment",
            Gender.FEMALE,
            Gender.FEMALE,
            new NullQuotePriceAdjustment()
          },
          {
            "with female named insured and other roommate should compute null adjustment",
            Gender.FEMALE,
            Gender.OTHER,
            new NullQuotePriceAdjustment()
          },
          {
            "with other named insured and male roommate should compute null adjustment",
            Gender.OTHER,
            Gender.MALE,
            new NullQuotePriceAdjustment()
          },
          {
            "with other named insured and female roommate should compute null adjustment",
            Gender.OTHER,
            Gender.FEMALE,
            new NullQuotePriceAdjustment()
          },
          {
            "with other named insured and other roommate should compute null adjustment",
            Gender.OTHER,
            Gender.OTHER,
            new NullQuotePriceAdjustment()
          },
        });
  }

  @Before
  public void setUp() {
    subject = new HardCodedRoommateAdjustmentProvider();
  }

  @Test
  public void gettingAdjustment() {
    QuotePriceAdjustment adjustment = subject.getAdjustment(namedInsuredGender, roommateGender);

    assertEquals(expectedAdjustment, adjustment);
  }
}
