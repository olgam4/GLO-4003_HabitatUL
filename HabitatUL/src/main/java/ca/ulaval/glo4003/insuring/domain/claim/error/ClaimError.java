package ca.ulaval.glo4003.insuring.domain.claim.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class ClaimError extends BaseError {
  public ClaimError(String error, String message) {
    super(error, message);
  }
}
