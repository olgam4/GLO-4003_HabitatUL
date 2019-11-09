package ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty;

import org.junit.Test;

import static ca.ulaval.glo4003.helper.quote.form.BikeGenerator.createBike;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BikeTest {
  private Bike subject;

  @Test
  public void checkingIfFormIsFilled_withUnfilledForm_shouldReturnFalse() {
    subject = new Bike(null, null, null, null);

    assertFalse(subject.isFilled());
  }

  @Test
  public void checkingIfFormIsFilled_withFilledForm_shouldReturnTrue() {
    subject = createBike();

    assertTrue(subject.isFilled());
  }
}
