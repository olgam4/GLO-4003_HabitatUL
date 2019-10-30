package ca.ulaval.glo4003.gateway.presentation.quote.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class CivilLiabilityRequestTest {
  private CivilLiabilityRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullLimit() {
    subject = new CivilLiabilityRequest(null);

    Set<ConstraintViolation<CivilLiabilityRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "limit", NotNull.class);
  }
}
