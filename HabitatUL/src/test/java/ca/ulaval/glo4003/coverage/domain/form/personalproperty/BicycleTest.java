package ca.ulaval.glo4003.coverage.domain.form.personalproperty;

import org.junit.Test;

import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.BicycleGenerator.createBicycle;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BicycleTest {
  private Bicycle subject;

  @Test
  public void checkingIfFormIsFilled_withUnfilledForm_shouldReturnFalse() {
    subject = new Bicycle(null, null, null, null);

    assertFalse(subject.isFilled());
  }

  @Test
  public void checkingIfFormIsFilled_withFilledForm_shouldReturnTrue() {
    subject = createBicycle();

    assertTrue(subject.isFilled());
  }
}
