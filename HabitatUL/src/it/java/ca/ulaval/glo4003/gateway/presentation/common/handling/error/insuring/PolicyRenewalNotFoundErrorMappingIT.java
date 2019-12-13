package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyRenewalNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.insuring.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;

public class PolicyRenewalNotFoundErrorMappingIT extends ErrorMappingIT {
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();

  @Override
  public Throwable getError() {
    return new PolicyRenewalNotFoundError(POLICY_RENEWAL_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "POLICY_RENEWAL_NOT_FOUND";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format(
        "sorry, can't find policy renewal with id <%s>", POLICY_RENEWAL_ID.toRepresentation());
  }
}
