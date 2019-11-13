package ca.ulaval.glo4003.gateway.presentation.policy.request;

import ca.ulaval.glo4003.gateway.presentation.coverage.request.BicycleRequest;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class ModificationRequestTest {
  private ModificationRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceValidBicycle() {
    subject = new ModificationRequest(new BicycleRequest(null, null, null, null));

    Set<ConstraintViolation<ModificationRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "bicycle");
  }
}
