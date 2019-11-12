package ca.ulaval.glo4003.gateway.presentation.quote.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;
import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.*;
import static ca.ulaval.glo4003.helper.coverage.form.identity.UniversityProfileGenerator.createUniversityProfileRequest;

public class IdentityRequestTest {
  private IdentityRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullFirstName() {
    subject =
        new IdentityRequest(
            null,
            createLastName(),
            createBirthDate(),
            createGender(),
            createUniversityProfileRequest());

    Set<ConstraintViolation<IdentityRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "firstName", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullLastName() {
    subject =
        new IdentityRequest(
            createFirstName(),
            null,
            createBirthDate(),
            createGender(),
            createUniversityProfileRequest());

    Set<ConstraintViolation<IdentityRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "lastName", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullBirthDate() {
    subject =
        new IdentityRequest(
            createFirstName(),
            createLastName(),
            null,
            createGender(),
            createUniversityProfileRequest());

    Set<ConstraintViolation<IdentityRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "birthDate", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullGender() {
    subject =
        new IdentityRequest(
            createFirstName(),
            createLastName(),
            createBirthDate(),
            null,
            createUniversityProfileRequest());

    Set<ConstraintViolation<IdentityRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "gender", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidUniversityProfile() {
    subject =
        new IdentityRequest(
            createFirstName(),
            createLastName(),
            createBirthDate(),
            createGender(),
            new UniversityProfileRequest(null, null, null));

    Set<ConstraintViolation<IdentityRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "universityProfile");
  }
}
