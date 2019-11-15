package ca.ulaval.glo4003.gateway.presentation.insuring.policy.request;

import ca.ulaval.glo4003.gateway.presentation.coverage.request.BicycleRequest;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class InsureBicycleRequestTest {
  private InsureBicycleRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullBicycle() {
    subject = new InsureBicycleRequest(null);

    Set<ConstraintViolation<InsureBicycleRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "bicycle", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceValidBicycle() {
    subject = new InsureBicycleRequest(new BicycleRequest(null, null, null, null));

    Set<ConstraintViolation<InsureBicycleRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "bicycle");
  }
}
