package ca.ulaval.glo4003.management.application.user.exception;

public class InvalidCredentialsException extends UserAppServiceException {
  private static final String ERROR = "INVALID_CREDENTIALS";
  private static final String MESSAGE = "wrong username or password";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
