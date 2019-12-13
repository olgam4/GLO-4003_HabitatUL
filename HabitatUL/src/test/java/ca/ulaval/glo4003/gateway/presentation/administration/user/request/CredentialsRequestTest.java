package ca.ulaval.glo4003.gateway.presentation.administration.user.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.administration.helper.user.CredentialsGenerator.createPassword;
import static ca.ulaval.glo4003.administration.helper.user.CredentialsGenerator.createUsername;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class CredentialsRequestTest {
  private CredentialsRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullUsername() {
    subject = new CredentialsRequest(null, createPassword());

    Set<ConstraintViolation<CredentialsRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "username", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullPassword() {
    subject = new CredentialsRequest(createUsername(), null);

    Set<ConstraintViolation<CredentialsRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "password", NotNull.class);
  }
}
