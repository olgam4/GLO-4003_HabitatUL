package ca.ulaval.glo4003.insuring.domain.policy.error;

import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;

public class PolicyModificationNotFoundError extends PolicyError {
  private static final String ERROR = "POLICY_MODIFICATION_NOT_FOUND";
  private static final String MESSAGE = "sorry, can't find policy modification with id <%s>";

  public PolicyModificationNotFoundError(PolicyModificationId policyModificationId) {
    super(ERROR, String.format(MESSAGE, policyModificationId.toRepresentation()));
  }
}
