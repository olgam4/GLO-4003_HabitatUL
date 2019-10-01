package ca.ulaval.glo4003.gateway.domain.user.exception;

import ca.ulaval.glo4003.gateway.domain.user.UserId;

public class UserNotFoundException extends UserException {
  private static final String ERROR = "USER_NOT_FOUND";
  private static final String MESSAGE = "can't find user with id <%s>";

  private final UserId userId;

  public UserNotFoundException(UserId userId) {
    this.userId = userId;
  }

  @Override
  public String getError() {
    return ERROR;
  }

  @Override
  public String getMessage() {
    return String.format(MESSAGE, userId.getValue());
  }
}
