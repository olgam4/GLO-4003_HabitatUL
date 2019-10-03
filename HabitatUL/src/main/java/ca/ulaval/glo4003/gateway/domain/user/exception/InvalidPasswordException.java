package ca.ulaval.glo4003.gateway.domain.user.exception;

public class InvalidPasswordException extends UserException {
  private static final String ERROR = "INVALID_PASSWORD";
  private static final String MESSAGE = "invalid provided password <%s>. Reason: <%s>";

  private final String password;
  private final String reason;

  public InvalidPasswordException(String password, String reason) {
    this.password = password;
    this.reason = reason;
  }

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE, password, reason);
  }
}
