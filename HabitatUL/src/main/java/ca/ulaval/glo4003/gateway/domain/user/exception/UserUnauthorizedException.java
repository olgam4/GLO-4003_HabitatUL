package ca.ulaval.glo4003.gateway.domain.user.exception;

public class UserUnauthorizedException extends UserException {
  private static final String ERROR = "UNAUTHORIZED";
  private static final String MESSAGE = "you must be signed in to access this resource";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
