package ca.ulaval.glo4003.management.domain.user.exception;

public class UserKeyNotFoundException extends UserException {
  private String userKey;

  public UserKeyNotFoundException(String userKey) {
    this.userKey = userKey;
  }

  public String getUserKey() {
    return userKey;
  }
}
