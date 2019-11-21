package ca.ulaval.glo4003.insuring.domain.policy.error;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

public class RenewalAlreadyCanceledError extends PolicyError {
  private static final String ERROR = "RENEWAL_ALREADY_CANCELED";
  private static final String MESSAGE =
      "sorry, policy renewal with id <%s> has already been canceled";

  public RenewalAlreadyCanceledError(PolicyRenewalId policyRenewalId) {
    super(ERROR, String.format(MESSAGE, policyRenewalId.toRepresentation()));
  }
}
