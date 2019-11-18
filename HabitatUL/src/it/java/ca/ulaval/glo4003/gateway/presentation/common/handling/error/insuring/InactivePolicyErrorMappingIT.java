package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;

public class InactivePolicyErrorMappingIT extends ErrorMappingIT {
  private static final PolicyId POLICY_ID = createPolicyId();

  @Override
  public Throwable getError() {
    return new InactivePolicyError(POLICY_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INACTIVE_POLICY";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, policy with id <%s> is inactive", POLICY_ID.toRepresentation());
  }
}
