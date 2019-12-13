package ca.ulaval.glo4003.coverage.domain.form.identity;

import org.junit.Test;

import static ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProgramGenerator.createUniversityProgram;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UniversityProgramTest {
  private UniversityProgram subject;

  @Test
  public void checkingIfFormIsFilled_withUnfilledForm_shouldReturnFalse() {
    subject = new UniversityProgram(null, null, null);

    assertFalse(subject.isFilled());
  }

  @Test
  public void checkingIfFormIsFilled_withFilledForm_shouldReturnTrue() {
    subject = createUniversityProgram();

    assertTrue(subject.isFilled());
  }
}
