package ca.ulaval.glo4003.administration.application.user.error;

public class InvalidCredentialsError extends UserAppServiceError {
  private static final String ERROR = "INVALID_CREDENTIALS";
  private static final String MESSAGE =
      "sorry, the provided username/password combination is invalid";

  public InvalidCredentialsError() {
    super(ERROR, MESSAGE);
  }
}
