package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HardCodedRoommateAdjustmentProviderTest {
  private HardCodedRoommateAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = new HardCodedRoommateAdjustmentProvider();
  }

  @Test
  public void
      gettingAdjustment_withAdjustedGenderCombination_shouldProvideCorrespondingAdjustment() {
    validateAdjustmentScenario(Gender.MALE, Gender.MALE, 0.1f);
  }

  @Test
  public void gettingAdjustment_withOtherGenderCombination_shouldProvideNoAdjustment() {
    validateNoAdjustmentScenario(Gender.MALE, Gender.FEMALE);
    validateNoAdjustmentScenario(Gender.MALE, Gender.OTHER);
    validateNoAdjustmentScenario(Gender.FEMALE, Gender.MALE);
    validateNoAdjustmentScenario(Gender.FEMALE, Gender.FEMALE);
    validateNoAdjustmentScenario(Gender.FEMALE, Gender.OTHER);
    validateNoAdjustmentScenario(Gender.OTHER, Gender.MALE);
    validateNoAdjustmentScenario(Gender.OTHER, Gender.FEMALE);
    validateNoAdjustmentScenario(Gender.OTHER, Gender.OTHER);
  }

  private void validateAdjustmentScenario(
      Gender namedInsuredGender, Gender roommateGender, float expectedFactor) {
    QuotePriceAdjustment adjustment = subject.getAdjustment(namedInsuredGender, roommateGender);

    QuotePriceAdjustment expectedAdjustment =
        new MultiplicativeQuotePriceAdjustment(expectedFactor);
    assertEquals(expectedAdjustment, adjustment);
  }

  private void validateNoAdjustmentScenario(Gender namedInsuredGender, Gender roommateGender) {
    QuotePriceAdjustment adjustment = subject.getAdjustment(namedInsuredGender, roommateGender);

    QuotePriceAdjustment expectedAdjustment = new NoQuotePriceAdjustment();
    assertEquals(expectedAdjustment, adjustment);
  }
}
