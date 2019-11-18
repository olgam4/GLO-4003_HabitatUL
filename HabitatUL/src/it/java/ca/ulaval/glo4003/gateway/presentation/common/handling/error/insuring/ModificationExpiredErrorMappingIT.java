package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.policy.error.ModificationExpiredError;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationId;

public class ModificationExpiredErrorMappingIT extends ErrorMappingIT {
  private static final PolicyModificationId POLICY_MODIFICATION_ID = createPolicyModificationId();

  @Override
  public Throwable getError() {
    return new ModificationExpiredError(POLICY_MODIFICATION_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "MODIFICATION_EXPIRED";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format(
        "sorry, policy modification with id <%s> is expired",
        POLICY_MODIFICATION_ID.toRepresentation());
  }
}
