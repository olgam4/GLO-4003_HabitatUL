package ca.ulaval.glo4003.gateway.presentation.insuring.claim.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class ProvideAuthorityNumberRequestTest {
  private ProvideAuthorityNumberRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullAuthorityNumber() {
    subject = new ProvideAuthorityNumberRequest(null);

    Set<ConstraintViolation<ProvideAuthorityNumberRequest>> violations =
        validator.validate(subject);

    assertViolationDetected(violations, "authorityNumber", NotNull.class);
  }
}
