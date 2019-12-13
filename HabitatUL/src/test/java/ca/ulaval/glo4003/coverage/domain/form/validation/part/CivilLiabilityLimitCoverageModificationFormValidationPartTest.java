package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.CivilLiabilityLimitError;
import ca.ulaval.glo4003.coverage.helper.form.CoverageModificationFormBuilder;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit.ONE_MILLION;
import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit.TWO_MILLION;
import static ca.ulaval.glo4003.coverage.domain.form.validation.part.CivilLiabilityLimitFormValidationPart.MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT;
import static ca.ulaval.glo4003.coverage.domain.form.validation.part.CivilLiabilityLimitFormValidationPart.MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT;
import static ca.ulaval.glo4003.shared.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class CivilLiabilityLimitCoverageModificationFormValidationPartTest {
  private static final int MIN_NUMBER_OF_UNITS = 1;

  @RunWith(Parameterized.class)
  public static class ValidTestCase extends TestCase {
    public ValidTestCase(
        String title,
        CivilLiabilityLimit civilLiabilityLimit,
        int minNumberOfUnits,
        int maxNumberOfUnits) {
      super(civilLiabilityLimit, minNumberOfUnits, maxNumberOfUnits);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {"with null limit should not throw", null, Integer.MIN_VALUE, Integer.MAX_VALUE},
            {
              "with 1M limit and 1-3 units should not throw",
              ONE_MILLION,
              MIN_NUMBER_OF_UNITS,
              MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT
            },
            {
              "with 1M limit and 4-49 units should not throw",
              ONE_MILLION,
              MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT,
              MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT
            },
            {
              "with 2M limit and 4-49 units should not throw",
              TWO_MILLION,
              MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT,
              MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT
            },
            {
              "with 2M limit and 50+ units should not throw",
              TWO_MILLION,
              MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT,
              Integer.MAX_VALUE
            },
          });
    }

    @Test
    public void validatingCoverageModificationForm() {
      super.validatingCoverageModificationForm();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCase extends TestCase {
    public InvalidTestCase(
        String title,
        CivilLiabilityLimit civilLiabilityLimit,
        int minNumberOfUnits,
        int maxNumberOfUnits) {
      super(civilLiabilityLimit, minNumberOfUnits, maxNumberOfUnits);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {
              "with 2M limit and 1-3 units should throw",
              TWO_MILLION,
              MIN_NUMBER_OF_UNITS,
              MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT
            },
            {
              "with 1M limit and 50+ units should throw",
              ONE_MILLION,
              MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT,
              Integer.MAX_VALUE
            },
          });
    }

    @Test(expected = CivilLiabilityLimitError.class)
    public void validatingCoverageModificationForm() {
      super.validatingCoverageModificationForm();
    }
  }

  public abstract static class TestCase {
    private CivilLiabilityLimitCoverageModificationFormValidationPart subject;
    private CivilLiabilityLimit civilLiabilityLimit;
    private int minNumberOfUnits;
    private int maxNumberOfUnits;

    public TestCase(
        CivilLiabilityLimit civilLiabilityLimit, int minNumberOfUnits, int maxNumberOfUnits) {
      this.civilLiabilityLimit = civilLiabilityLimit;
      this.minNumberOfUnits = minNumberOfUnits;
      this.maxNumberOfUnits = maxNumberOfUnits;
    }

    @Before
    public void setUp() {
      subject = new CivilLiabilityLimitCoverageModificationFormValidationPart();
    }

    public void validatingCoverageModificationForm() {
      int numberOfUnits =
          Faker.instance().number().numberBetween(minNumberOfUnits, maxNumberOfUnits);
      CoverageModificationForm coverageModificationForm =
          CoverageModificationFormBuilder.aCoverageModificationForm()
              .withCivilLiabilityLimit(civilLiabilityLimit)
              .withNumberOfUnits(numberOfUnits)
              .build();

      subject.validate(coverageModificationForm);
    }
  }
}
