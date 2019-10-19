package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public abstract class UlRegistrarOfficeIT {
  private static final String IDUL = Faker.instance().university().name();
  private static final String IDENTIFICATION_NUMBER = Faker.instance().idNumber().ssnValid();
  private static final String PROGRAM = Faker.instance().educator().course();

  private UlRegistrarOffice subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void validatingRegistration_withValidRegistration_shouldConfirmRegistration() {
    assertTrue(subject.isValidRegistration(IDUL, IDENTIFICATION_NUMBER, PROGRAM));
  }

  public abstract UlRegistrarOffice createSubject();
}
