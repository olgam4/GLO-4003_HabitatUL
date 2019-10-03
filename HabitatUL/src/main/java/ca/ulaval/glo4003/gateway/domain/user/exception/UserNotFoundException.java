package ca.ulaval.glo4003.gateway.domain.user.exception;

import ca.ulaval.glo4003.gateway.domain.user.UserId;

public class UserNotFoundException extends UserException {
  private static final String ERROR = "USER_NOT_FOUND";
  private static final String MESSAGE = "can't find user <%s>";

  private final String identifier;

  public UserNotFoundException(UserId userId) {
    this.identifier = userId.getValue().toString();
  }

  public UserNotFoundException(String username) {
    this.identifier = username;
  }

  @Override
  public String getError() {
    return ERROR;
  }

  @Override
  public String getMessage() {
    return String.format(MESSAGE, identifier);
  }
}
