package ca.ulaval.glo4003.coverage.domain.policy.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class PolicyError extends BaseError {
  public PolicyError(String error, String message) {
    super(error, message);
  }
}
