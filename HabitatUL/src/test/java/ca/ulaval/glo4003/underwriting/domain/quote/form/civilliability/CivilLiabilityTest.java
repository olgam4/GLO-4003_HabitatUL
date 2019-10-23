package ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability;

import ca.ulaval.glo4003.helper.quote.form.CivilLiabilityGenerator;
import com.github.javafaker.Faker;
import org.junit.Test;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability.MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT;
import static org.junit.Assert.*;

public class CivilLiabilityTest {
  private static final int MIN_NUMBER_OF_UNITS = 1;

  private CivilLiability subject;

  @Test
  public void completingCivilLiability_withAmount_shouldUseProvidedAmount() {
    validateScenario(
        CivilLiabilityLimit.ONE_MILLION,
        CivilLiabilityLimit.ONE_MILLION,
        MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT,
        Integer.MAX_VALUE);
    validateScenario(
        CivilLiabilityLimit.TWO_MILLION,
        CivilLiabilityLimit.TWO_MILLION,
        MIN_NUMBER_OF_UNITS,
        MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT);
  }

  @Test
  public void completingCivilLiability_withoutAmount_shouldUseDefaultAmount() {
    validateScenario(
        CivilLiabilityLimit.ONE_MILLION,
        null,
        MIN_NUMBER_OF_UNITS,
        MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT);
    validateScenario(
        CivilLiabilityLimit.TWO_MILLION,
        null,
        MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_LIMIT,
        Integer.MAX_VALUE);
  }

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

  private void validateScenario(
      CivilLiabilityLimit expectedAmount,
      CivilLiabilityLimit initialAmount,
      int minNumberOfUnits,
      int maxNumberOfUnits) {
    subject = new CivilLiability(initialAmount);
    int numberOfUnits = Faker.instance().number().numberBetween(minNumberOfUnits, maxNumberOfUnits);

    CivilLiability civilLiability = subject.completeWithDefaultValues(numberOfUnits);

    assertEquals(expectedAmount, civilLiability.getLimit());
  }
}
