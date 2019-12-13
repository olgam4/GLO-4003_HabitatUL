package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProfileGenerator.createIdul;
import static ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProfileGenerator.createNi;
import static ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProgramGenerator.createUniversityProgramRequest;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class UniversityProfileRequestTest {
  private UniversityProfileRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullIdul() {
    subject = new UniversityProfileRequest(null, createNi(), createUniversityProgramRequest());

    Set<ConstraintViolation<UniversityProfileRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "idul", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullNi() {
    subject = new UniversityProfileRequest(createIdul(), null, createUniversityProgramRequest());

    Set<ConstraintViolation<UniversityProfileRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "ni", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullProgram() {
    subject = new UniversityProfileRequest(createIdul(), createNi(), null);

    Set<ConstraintViolation<UniversityProfileRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "program", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidProgram() {
    subject =
        new UniversityProfileRequest(
            createIdul(), createNi(), new UniversityProgramRequest(null, null, null));

    Set<ConstraintViolation<UniversityProfileRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "program");
  }
}
