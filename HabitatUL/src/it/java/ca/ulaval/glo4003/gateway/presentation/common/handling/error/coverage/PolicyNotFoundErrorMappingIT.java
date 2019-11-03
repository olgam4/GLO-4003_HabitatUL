package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.coverage.domain.policy.error.PolicyNotFoundError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.helper.policy.PolicyGenerator;

import javax.ws.rs.core.Response;

public class PolicyNotFoundErrorMappingIT extends ErrorMappingIT {
  private static final PolicyId POLICY_ID = PolicyGenerator.createPolicyId();

  @Override
  public Throwable getError() {
    return new PolicyNotFoundError(POLICY_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.NOT_FOUND.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "POLICY_NOT_FOUND";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, can't find policy with id <%s>", POLICY_ID.toRepresentation());
  }
}
