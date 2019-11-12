package ca.ulaval.glo4003.insuring.application.policy.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class PolicyAppServiceError extends BaseError {
  public PolicyAppServiceError(String error, String message) {
    super(error, message);
  }
}
