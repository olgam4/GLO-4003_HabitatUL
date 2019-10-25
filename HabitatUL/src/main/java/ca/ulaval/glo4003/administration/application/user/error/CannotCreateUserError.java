package ca.ulaval.glo4003.administration.application.user.error;

public class CannotCreateUserError extends UserAppServiceError {
  private static final String ERROR = "CANNOT_CREATE_USER";
  private static final String MESSAGE = "reason not provided";

  private final String message;

  public CannotCreateUserError() {
    message = MESSAGE;
  }

  public CannotCreateUserError(String reason) {
    message = reason;
  }

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return message;
  }
}
