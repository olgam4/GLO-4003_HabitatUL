package ca.ulaval.glo4003.administration.application.user.error;

import ca.ulaval.glo4003.shared.domain.handling.BaseError;

public abstract class UserAppServiceError extends BaseError {
  public UserAppServiceError(String error, String message) {
    super(error, message);
  }
}
