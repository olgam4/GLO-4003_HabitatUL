package ca.ulaval.glo4003.management.domain.user.exception;

public class UsernameKeyNotFoundException extends UserException {
  private String usernameKey;

  public UsernameKeyNotFoundException(String usernameKey) {
    this.usernameKey = usernameKey;
  }

  public String getUsernameKey() {
    return usernameKey;
  }
}
