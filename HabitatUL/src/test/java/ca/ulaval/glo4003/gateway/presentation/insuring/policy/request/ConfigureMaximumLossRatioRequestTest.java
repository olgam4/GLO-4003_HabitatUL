package ca.ulaval.glo4003.gateway.presentation.insuring.policy.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;

public class ConfigureMaximumLossRatioRequestTest {
  private ConfigureMaximumLossRatioRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullMaximumLossRatio() {
    subject = new ConfigureMaximumLossRatioRequest(null);

    Set<ConstraintViolation<ConfigureMaximumLossRatioRequest>> violations =
        validator.validate(subject);

    assertViolationDetected(violations, "maximumLossRatio", NotNull.class);
  }
}
