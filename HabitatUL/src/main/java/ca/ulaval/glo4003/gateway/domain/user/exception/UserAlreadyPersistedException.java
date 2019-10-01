package ca.ulaval.glo4003.gateway.domain.user.exception;

import ca.ulaval.glo4003.gateway.domain.user.UserId;

public class UserAlreadyPersistedException extends UserException {
  private static final String ERROR = "USER_ALREADY_PERSISTED";
  private static final String MESSAGE = "user with id <%s> is already persisted";

  private final UserId userId;

  public UserAlreadyPersistedException(UserId userId) {
    this.userId = userId;
  }

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE, userId.getValue());
  }
}
