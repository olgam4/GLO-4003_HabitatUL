package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.policy.error.RenewalNotYetAcceptedError;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;

public class RenewalNotYetAccepted extends ErrorMappingIT {
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();

  @Override
  public Throwable getError() {
    return new RenewalNotYetAcceptedError(POLICY_RENEWAL_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "RENEWAL_NOT_YET_ACCEPTED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format(
        "sorry, policy renewal with id <%s> is not yet accepted",
        POLICY_RENEWAL_ID.toRepresentation());
  }
}
