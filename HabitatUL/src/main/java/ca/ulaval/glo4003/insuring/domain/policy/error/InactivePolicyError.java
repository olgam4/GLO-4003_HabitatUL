package ca.ulaval.glo4003.insuring.domain.policy.error;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;

public class InactivePolicyError extends PolicyError {
  private static final String ERROR = "INACTIVE_POLICY";
  private static final String MESSAGE = "sorry, policy with id <%s> is inactive";

  public InactivePolicyError(PolicyId policyId) {
    super(ERROR, String.format(MESSAGE, policyId.toRepresentation()));
  }
}
