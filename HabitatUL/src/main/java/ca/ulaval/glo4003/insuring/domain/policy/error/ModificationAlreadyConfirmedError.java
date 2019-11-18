package ca.ulaval.glo4003.insuring.domain.policy.error;

import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;

public class ModificationAlreadyConfirmedError extends PolicyError {
  private static final String ERROR = "MODIFICATION_ALREADY_CONFIRMED";
  private static final String MESSAGE =
      "sorry, policy modification with id <%s> has already been confirmed";

  public ModificationAlreadyConfirmedError(PolicyModificationId policyModificationId) {
    super(ERROR, String.format(MESSAGE, policyModificationId.toRepresentation()));
  }
}
