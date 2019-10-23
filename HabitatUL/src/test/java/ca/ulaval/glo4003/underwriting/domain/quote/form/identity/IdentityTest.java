package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import ca.ulaval.glo4003.helper.quote.form.IdentityGenerator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IdentityTest {
  private Identity subject;

  @Test
  public void checkingIfFormIsFilled_withUnfilledForm_shouldReturnFalse() {
    subject = new Identity(null, null, null, null, UniversityProfile.UNFILLED_UNIVERSITY_PROFILE);

    assertFalse(subject.isFilled());
  }

  @Test
  public void checkingIfFormIsFilled_withFilledForm_shouldReturnTrue() {
    subject = IdentityGenerator.createIdentity();

    assertTrue(subject.isFilled());
  }
}
