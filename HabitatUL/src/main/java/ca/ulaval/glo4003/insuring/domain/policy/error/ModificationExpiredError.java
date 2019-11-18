package ca.ulaval.glo4003.insuring.domain.policy.error;

import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;

public class ModificationExpiredError extends PolicyError {
  private static final String ERROR = "MODIFICATION_EXPIRED";
  private static final String MESSAGE = "sorry, policy modification with id <%s> is expired";

  public ModificationExpiredError(PolicyModificationId policyModificationId) {
    super(ERROR, String.format(MESSAGE, policyModificationId.toRepresentation()));
  }
}
