package ca.ulaval.glo4003.insuring.domain.policy.error;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

public class RenewalNotYetAcceptedError extends PolicyError {
  private static final String ERROR = "RENEWAL_NOT_YET_ACCEPTED";
  private static final String MESSAGE = "sorry, policy renewal with id <%s> is not yet accepted";

  public RenewalNotYetAcceptedError(PolicyRenewalId policyRenewalId) {
    super(ERROR, String.format(MESSAGE, policyRenewalId.toRepresentation()));
  }
}
