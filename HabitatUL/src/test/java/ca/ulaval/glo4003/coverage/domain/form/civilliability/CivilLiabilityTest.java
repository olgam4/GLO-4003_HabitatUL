package ca.ulaval.glo4003.coverage.domain.form.civilliability;

import ca.ulaval.glo4003.helper.coverage.form.civilliability.CivilLiabilityGenerator;
import com.github.javafaker.Faker;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability.MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class CivilLiabilityTest {
  private static final int MIN_NUMBER_OF_UNITS = 1;

  private CivilLiability subject;

  @Test
  public void checkingIfFormIsFilled_withUnfilledForm_shouldReturnFalse() {
    subject = new CivilLiability(null);

    assertFalse(subject.isFilled());
  }

  @Test
  public void checkingIfFormIsFilled_withFilledForm_shouldReturnTrue() {
    subject = CivilLiabilityGenerator.createCivilLiability();

    assertTrue(subject.isFilled());
  }

  @RunWith(Parameterized.class)
  public static class CompletingCivilLiabilityTest {
    private CivilLiabilityLimit expectedAmount;
    private CivilLiabilityLimit initialAmount;
    private int minNumberOfUnits;
    private int maxNumberOfUnits;

    public CompletingCivilLiabilityTest(
        String title,
        CivilLiabilityLimit expectedAmount,
        CivilLiabilityLimit initialAmount,
        int minNumberOfUnits,
        int maxNumberOfUnits) {
      this.expectedAmount = expectedAmount;
      this.initialAmount = initialAmount;
      this.minNumberOfUnits = minNumberOfUnits;
      this.maxNumberOfUnits = maxNumberOfUnits;
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {
              "with 1M provided limit and 2M default limit should use provided limit",
              CivilLiabilityLimit.ONE_MILLION,
              CivilLiabilityLimit.ONE_MILLION,
              MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT,
              Integer.MAX_VALUE
            },
            {
              "with 2M provided limit and 1M default limit should use provided limit",
              CivilLiabilityLimit.TWO_MILLION,
              CivilLiabilityLimit.TWO_MILLION,
              MIN_NUMBER_OF_UNITS,
              MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT
            },
            {
              "without provided limit and 1M default limit should use default limit",
              CivilLiabilityLimit.ONE_MILLION,
              null,
              MIN_NUMBER_OF_UNITS,
              MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT
            },
            {
              "without provided limit and 2M default limit should use default limit",
              CivilLiabilityLimit.TWO_MILLION,
              null,
              MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT,
              Integer.MAX_VALUE
            },
          });
    }

    @Test
    public void completingCivilLiability() {
      CivilLiability subject = new CivilLiability(initialAmount);
      int numberOfUnits =
          Faker.instance().number().numberBetween(minNumberOfUnits, maxNumberOfUnits);

      CivilLiability civilLiability = subject.completeWithDefaultValues(numberOfUnits);

      assertEquals(expectedAmount, civilLiability.getLimit());
    }
  }
}
