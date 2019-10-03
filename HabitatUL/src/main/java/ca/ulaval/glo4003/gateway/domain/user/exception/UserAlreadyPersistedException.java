package ca.ulaval.glo4003.gateway.domain.user.exception;

import ca.ulaval.glo4003.gateway.domain.user.UserId;

public class UserAlreadyPersistedException extends UserException {
  private static final String ERROR = "USER_ALREADY_PERSISTED";
  private static final String MESSAGE = "user <%s> is already persisted";

  private final String identifier;

  public UserAlreadyPersistedException(UserId userId) {
    this.identifier = userId.getValue().toString();
  }

  public UserAlreadyPersistedException(String username) {
    this.identifier = username;
  }

  public String getError() {
    return ERROR;
  }

  public String getMessage() {
    return String.format(MESSAGE, identifier);
  }
}
