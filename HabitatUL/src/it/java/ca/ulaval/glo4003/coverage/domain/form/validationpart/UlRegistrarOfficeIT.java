package ca.ulaval.glo4003.coverage.domain.form.validationpart;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.coverage.form.identity.UniversityProfileGenerator.createIdul;
import static ca.ulaval.glo4003.helper.coverage.form.identity.UniversityProfileGenerator.createNi;
import static ca.ulaval.glo4003.helper.coverage.form.identity.UniversityProgramGenerator.createUniversityProgram;
import static org.junit.Assert.assertTrue;

public abstract class UlRegistrarOfficeIT {
  private static final String IDUL = createIdul();
  private static final String IDENTIFICATION_NUMBER = createNi();
  private static final UniversityProgram PROGRAM = createUniversityProgram();

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
