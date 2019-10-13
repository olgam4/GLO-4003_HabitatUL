package ca.ulaval.glo4003.administration.application.user.exception;

public class InvalidCredentialsError extends UserAppServiceError {
  private static final String ERROR = "INVALID_CREDENTIALS";
  private static final String MESSAGE = "wrong username or password";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
