package ca.ulaval.glo4003.administration.application.user.error;

public class CouldNotAuthenticateUserError extends UserAppServiceError {
  private static final String ERROR = "COULD_NOT_AUTHENTICATE_USER";
  private static final String MESSAGE =
      "sorry, something went wrong while trying to authenticate your identity";

  public CouldNotAuthenticateUserError() {
    super(ERROR, MESSAGE);
  }
}
