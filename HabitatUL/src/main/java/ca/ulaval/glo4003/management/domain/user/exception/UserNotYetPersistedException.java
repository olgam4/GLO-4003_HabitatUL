package ca.ulaval.glo4003.management.domain.user.exception;

import ca.ulaval.glo4003.management.domain.user.UserId;

public class UserNotYetPersistedException extends UserException {
  public UserNotYetPersistedException(UserId userId) {}

  public UserNotYetPersistedException(String username) {}
}
