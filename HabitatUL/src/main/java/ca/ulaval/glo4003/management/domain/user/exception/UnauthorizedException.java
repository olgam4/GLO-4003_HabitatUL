package ca.ulaval.glo4003.management.domain.user.exception;

public class UnauthorizedException extends UserException {
  private static final String ERROR = "UNAUTHORIZED";
  private static final String MESSAGE = "you do not have necessary rights to access this resource";

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return MESSAGE;
  }
}
