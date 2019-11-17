package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyModificationNotFoundError;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationId;

public class PolicyModificationNotFoundErrorMappingIT extends ErrorMappingIT {
  private static final PolicyModificationId POLICY_MODIFICATION_ID = createPolicyModificationId();

  @Override
  public Throwable getError() {
    return new PolicyModificationNotFoundError(POLICY_MODIFICATION_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "POLICY_MODIFICATION_NOT_FOUND";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format(
        "sorry, can't find policy modification with id <%s>",
        POLICY_MODIFICATION_ID.toRepresentation());
  }
}
