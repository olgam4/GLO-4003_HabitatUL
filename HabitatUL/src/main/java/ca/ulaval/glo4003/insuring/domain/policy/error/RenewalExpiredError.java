package ca.ulaval.glo4003.insuring.domain.policy.error;

import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;

public class RenewalExpiredError extends PolicyError {
  private static final String ERROR = "RENEWAL_EXPIRED";
  private static final String MESSAGE = "sorry, policy renewal with id <%s> is expired";

  public RenewalExpiredError(PolicyRenewalId policyRenewalId) {
    super(ERROR, String.format(MESSAGE, policyRenewalId.toRepresentation()));
  }
}
