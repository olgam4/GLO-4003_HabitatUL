package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import org.junit.Test;

import static ca.ulaval.glo4003.calculator.domain.input.UniversityProgram.UNFILLED_UNIVERSITY_PROGRAM;
import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.createUniversityProfile;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UniversityProfileTest {
  private UniversityProfile subject;

  @Test
  public void checkingIfFormIsFilled_withUnfilledForm_shouldReturnFalse() {
    subject = new UniversityProfile(null, null, UNFILLED_UNIVERSITY_PROGRAM);

    assertFalse(subject.isFilled());
  }

  @Test
  public void checkingIfFormIsFilled_withFilledForm_shouldReturnTrue() {
    subject = createUniversityProfile();

    assertTrue(subject.isFilled());
  }
}
