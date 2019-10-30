package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.*;
import static org.junit.Assert.assertTrue;

public abstract class UlRegistrarOfficeIT {
  private static final String IDUL = createIdul();
  private static final String IDENTIFICATION_NUMBER = createNi();
  private static final String CYCLE = createCycle();
  private static final String DEGREE = createDegree();
  private static final String PROGRAM = createProgram();

  private UlRegistrarOffice subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void validatingRegistration_withValidRegistration_shouldConfirmRegistration() {
    assertTrue(subject.isValidRegistration(IDUL, IDENTIFICATION_NUMBER, CYCLE, DEGREE, PROGRAM));
  }

  public abstract UlRegistrarOffice createSubject();
}
