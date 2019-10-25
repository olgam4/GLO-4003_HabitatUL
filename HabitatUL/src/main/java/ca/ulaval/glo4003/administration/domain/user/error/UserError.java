package ca.ulaval.glo4003.administration.domain.user.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class UserError extends BaseError {
  public UserError(String error, String message) {
    super(error, message);
  }
}
