package ca.ulaval.glo4003.coverage.domain.policy.error;

import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;

public class PolicyNotFoundError extends PolicyError {
  private static final String ERROR = "POLICY_NOT_FOUND";
  private static final String MESSAGE = "sorry, can't find policy with id <%s>";

  public PolicyNotFoundError(PolicyId policyId) {
    super(ERROR, String.format(MESSAGE, policyId.toRepresentation()));
  }
}
