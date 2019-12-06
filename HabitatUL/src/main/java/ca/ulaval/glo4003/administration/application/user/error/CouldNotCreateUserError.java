package ca.ulaval.glo4003.administration.application.user.error;

public class CouldNotCreateUserError extends UserAppServiceError {
  private static final String ERROR = "COULD_NOT_CREATE_USER";
  private static final String MESSAGE =
      "sorry, something went wrong while trying to create your user profile";

  public CouldNotCreateUserError() {
    super(ERROR, MESSAGE);
  }

  public CouldNotCreateUserError(Throwable cause) {
    super(ERROR, MESSAGE, cause);
  }
}
