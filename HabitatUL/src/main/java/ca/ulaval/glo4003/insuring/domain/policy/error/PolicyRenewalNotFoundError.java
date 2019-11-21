package ca.ulaval.glo4003.insuring.domain.policy.error;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

public class PolicyRenewalNotFoundError extends PolicyError {
  private static final String ERROR = "POLICY_RENEWAL_NOT_FOUND";
  private static final String MESSAGE = "sorry, can't find policy renewal with id <%s>";

  public PolicyRenewalNotFoundError(PolicyRenewalId policyRenewalId) {
    super(ERROR, String.format(MESSAGE, policyRenewalId.toRepresentation()));
  }
}
