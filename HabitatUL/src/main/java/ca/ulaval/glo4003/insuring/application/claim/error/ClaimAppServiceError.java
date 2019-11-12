package ca.ulaval.glo4003.insuring.application.claim.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class ClaimAppServiceError extends BaseError {
  public ClaimAppServiceError(String error, String message) {
    super(error, message);
  }
}
