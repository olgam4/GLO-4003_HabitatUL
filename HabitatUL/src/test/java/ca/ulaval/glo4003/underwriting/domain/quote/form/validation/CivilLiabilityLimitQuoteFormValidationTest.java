package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.helper.quote.form.BuildingBuilder;
import ca.ulaval.glo4003.helper.quote.form.CivilLiabilityBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteCivilLiabilityLimitError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.validation.CivilLiabilityLimitQuoteFormValidation.MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT;
import static ca.ulaval.glo4003.underwriting.domain.quote.form.validation.CivilLiabilityLimitQuoteFormValidation.MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT;

public class CivilLiabilityLimitQuoteFormValidationTest {
  private static final int MIN_NUMBER_OF_UNITS = 1;

  private CivilLiabilityLimitQuoteFormValidation subject;

  @Before
  public void setUp() {
    subject = new CivilLiabilityLimitQuoteFormValidation();
  }

  @Test
  public void validatingQuoteForm_withValidCivilLiabilityLimit_shouldNotThrow() {
    validateScenario(
        CivilLiabilityLimit.ONE_MILLION,
        MIN_NUMBER_OF_UNITS,
        MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT);
    validateScenario(
        CivilLiabilityLimit.ONE_MILLION,
        MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT,
        MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT);
    validateScenario(
        CivilLiabilityLimit.TWO_MILLION,
        MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT,
        MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT);
    validateScenario(
        CivilLiabilityLimit.TWO_MILLION,
        MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT,
        Integer.MAX_VALUE);
  }

  @Test(expected = QuoteCivilLiabilityLimitError.class)
  public void validatingQuoteForm_withTooBigCivilLiabilityLimit_shouldThrow() {
    validateScenario(
        CivilLiabilityLimit.TWO_MILLION,
        MIN_NUMBER_OF_UNITS,
        MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_LIMIT);
  }

  @Test(expected = QuoteCivilLiabilityLimitError.class)
  public void validatingQuoteForm_withTooSmallCivilLiabilityLimit_shouldThrow() {
    validateScenario(
        CivilLiabilityLimit.ONE_MILLION,
        MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_LIMIT,
        Integer.MAX_VALUE);
  }

  private void validateScenario(
      CivilLiabilityLimit civilLiabilityLimit, int minNumberOfUnits, int maxNumberOfUnits) {
    int numberOfUnits = Faker.instance().number().numberBetween(minNumberOfUnits, maxNumberOfUnits);
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
