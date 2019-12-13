package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProgramGenerator.*;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class UniversityProgramRequestTest {
  private UniversityProgramRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullCycle() {
    subject = new UniversityProgramRequest(null, createDegree(), createMajor());

    Set<ConstraintViolation<UniversityProgramRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "cycle", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullDegree() {
    subject = new UniversityProgramRequest(createCycle(), null, createMajor());

    Set<ConstraintViolation<UniversityProgramRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "degree", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullMajor() {
    subject = new UniversityProgramRequest(createCycle(), createDegree(), null);

    Set<ConstraintViolation<UniversityProgramRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "major", NotNull.class);
  }
}
