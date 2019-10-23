package ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability;

import ca.ulaval.glo4003.helper.quote.form.CivilLiabilityGenerator;
import com.github.javafaker.Faker;
import org.junit.Test;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability.MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT;
import static org.junit.Assert.*;

public class CivilLiabilityTest {
  private static final int MIN_NUMBER_OF_UNITS = 1;

  private CivilLiability subject;

  @Test
  public void completingCivilLiability_withAmount_shouldUseProvidedAmount() {
    validateScenario(
        CivilLiabilityAmount.ONE_MILLION,
        CivilLiabilityAmount.ONE_MILLION,
        MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT,
        Integer.MAX_VALUE);
    validateScenario(
        CivilLiabilityAmount.TWO_MILLION,
        CivilLiabilityAmount.TWO_MILLION,
        MIN_NUMBER_OF_UNITS,
        MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT);
  }

  @Test
  public void completingCivilLiability_withoutAmount_shouldUseDefaultAmount() {
    validateScenario(
        CivilLiabilityAmount.ONE_MILLION,
        null,
        MIN_NUMBER_OF_UNITS,
        MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT);
    validateScenario(
        CivilLiabilityAmount.TWO_MILLION,
        null,
        MIN_NB_UNITS_DEFAULT_HIGHER_CIVIL_LIABILITY_AMOUNT,
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
      CivilLiabilityAmount expectedAmount,
      CivilLiabilityAmount initialAmount,
      int minNumberOfUnits,
      int maxNumberOfUnits) {
    subject = new CivilLiability(initialAmount);
    int numberOfUnits = Faker.instance().number().numberBetween(minNumberOfUnits, maxNumberOfUnits);

    CivilLiability civilLiability = subject.completeWithDefaultValues(numberOfUnits);

    assertEquals(expectedAmount, civilLiability.getCivilLiabilityAmount());
  }
}
