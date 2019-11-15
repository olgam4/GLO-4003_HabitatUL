package ca.ulaval.glo4003.gateway.presentation.policy.request;

import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.assertViolationDetected;
import static ca.ulaval.glo4003.gateway.presentation.ValidationTestHelper.getValidator;
import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createSinisterType;
import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createLossDeclarations;

public class ClaimRequestTest {
  private ClaimRequest subject;
  private Validator validator;

  @Before
  public void setUp() {
    validator = getValidator();
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullSinisterType() {
    subject = new ClaimRequest(null, createLossDeclarations());

    Set<ConstraintViolation<ClaimRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "sinisterType", NotNull.class);
  }

  @Test
  public void validatingRequest_shouldEnforceNotNullLossDeclaration() {
    subject = new ClaimRequest(createSinisterType(), null);

    Set<ConstraintViolation<ClaimRequest>> violations = validator.validate(subject);

    assertViolationDetected(violations, "lossDeclarations", NotNull.class);
  }
}
