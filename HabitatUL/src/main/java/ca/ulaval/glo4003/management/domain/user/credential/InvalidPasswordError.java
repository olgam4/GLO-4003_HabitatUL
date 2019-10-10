package ca.ulaval.glo4003.management.domain.user.credential;

import ca.ulaval.glo4003.management.domain.user.exception.UserError;

public class InvalidPasswordError extends UserError {
  private static final String ERROR = "INVALID_PASSWORD";
  private static final String MESSAGE = "invalid provided password <%s>. Reason: <%s>";

  private final String password;
  private final String reason;

  public InvalidPasswordError(String password, String reason) {
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
