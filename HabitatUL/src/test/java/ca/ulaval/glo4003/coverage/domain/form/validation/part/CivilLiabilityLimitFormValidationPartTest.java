package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.validation.part.error.CivilLiabilityLimitError;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.coverage.form.building.BuildingBuilder;
import ca.ulaval.glo4003.helper.coverage.form.civilliability.CivilLiabilityBuilder;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.coverage.domain.form.validation.part.CivilLiabilityLimitFormValidationPart.MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT;
import static ca.ulaval.glo4003.coverage.domain.form.validation.part.CivilLiabilityLimitFormValidationPart.MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class CivilLiabilityLimitFormValidationPartTest {
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
            {
              "with 1M limit and 1-3 units should not throw",
              CivilLiabilityLimit.ONE_MILLION,
              MIN_NUMBER_OF_UNITS,
              MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT
            },
            {
              "with 1M limit and 4-49 units should not throw",
              CivilLiabilityLimit.ONE_MILLION,
              MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT,
              MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT
            },
            {
              "with 2M limit and 4-49 units should not throw",
              CivilLiabilityLimit.TWO_MILLION,
              MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT,
              MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT
            },
            {
              "with 2M limit and 50+ units should not throw",
              CivilLiabilityLimit.TWO_MILLION,
              MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT,
              Integer.MAX_VALUE
            },
          });
    }

    @Test
    public void validatingQuoteForm() {
      super.validatingQuoteForm();
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
              CivilLiabilityLimit.TWO_MILLION,
              MIN_NUMBER_OF_UNITS,
              MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT
            },
            {
              "with 1M limit and 50+ units should throw",
              CivilLiabilityLimit.ONE_MILLION,
              MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT,
              Integer.MAX_VALUE
            },
          });
    }

    @Test(expected = CivilLiabilityLimitError.class)
    public void validatingQuoteForm() {
      super.validatingQuoteForm();
    }
  }

  public abstract static class TestCase {
    private CivilLiabilityLimitFormValidationPart subject;
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
      subject = new CivilLiabilityLimitFormValidationPart();
    }

    public void validatingQuoteForm() {
      int numberOfUnits =
          Faker.instance().number().numberBetween(minNumberOfUnits, maxNumberOfUnits);
      Building building = BuildingBuilder.aBuilding().withNumberOfUnits(numberOfUnits).build();
      CivilLiability civilLiability =
          CivilLiabilityBuilder.aCivilLiability().withAmount(civilLiabilityLimit).build();
      QuoteForm quoteForm =
          QuoteFormBuilder.aQuoteForm()
              .withBuilding(building)
              .withCivilLiability(civilLiability)
              .build();

      subject.validate(quoteForm);
    }
  }
}
