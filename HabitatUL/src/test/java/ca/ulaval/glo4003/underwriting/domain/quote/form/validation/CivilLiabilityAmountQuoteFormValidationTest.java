package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteCivilLiabilityError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.validation.CivilLiabilityAmountQuoteFormValidation.MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_AMOUNT;
import static ca.ulaval.glo4003.underwriting.domain.quote.form.validation.CivilLiabilityAmountQuoteFormValidation.MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_AMOUNT;

public class CivilLiabilityAmountQuoteFormValidationTest {
  private static final int MIN_NUMBER_OF_UNITS = 1;

  private CivilLiabilityAmountQuoteFormValidation subject;

  @Before
  public void setUp() {
    subject = new CivilLiabilityAmountQuoteFormValidation();
  }

  @Test
  public void validatingQuoteForm_withValidCivilLiabilityAmount_shouldNotThrow() {
    validateScenario(
        CivilLiabilityAmount.ONE_MILLION,
        MIN_NUMBER_OF_UNITS,
        MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_AMOUNT);
    validateScenario(
        CivilLiabilityAmount.ONE_MILLION,
        MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_AMOUNT,
        MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_AMOUNT);
    validateScenario(
        CivilLiabilityAmount.TWO_MILLION,
        MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_AMOUNT,
        MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_AMOUNT);
    validateScenario(
        CivilLiabilityAmount.TWO_MILLION,
        MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_AMOUNT,
        Integer.MAX_VALUE);
  }

  @Test(expected = QuoteCivilLiabilityError.class)
  public void validatingQuoteForm_withTooBigCivilLiabilityAmount_shouldThrow() {
    validateScenario(
        CivilLiabilityAmount.TWO_MILLION,
        MIN_NUMBER_OF_UNITS,
        MIN_NUMBER_OF_UNITS_FOR_HIGHER_CIVIL_LIABILITY_AMOUNT);
  }

  @Test(expected = QuoteCivilLiabilityError.class)
  public void validatingQuoteForm_withTooSmallCivilLiabilityAmount_shouldThrow() {
    validateScenario(
        CivilLiabilityAmount.ONE_MILLION,
        MAX_NUMBER_OF_UNITS_FOR_LOWER_CIVIL_LIABILITY_AMOUNT,
        Integer.MAX_VALUE);
  }

  private void validateScenario(
      CivilLiabilityAmount civilLiabilityAmount, int minNumberOfUnits, int maxNumberOfUnits) {
    int numberOfUnits = Faker.instance().number().numberBetween(minNumberOfUnits, maxNumberOfUnits);
    QuoteForm quoteForm =
        QuoteFormGenerator.createQuoteFormWithCivilLiabilityAmountAndNumberOfUnits(
            civilLiabilityAmount, numberOfUnits);

    subject.validate(quoteForm);
  }
}
