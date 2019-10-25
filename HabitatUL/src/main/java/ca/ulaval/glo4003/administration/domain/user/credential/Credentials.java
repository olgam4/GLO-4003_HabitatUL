package ca.ulaval.glo4003.administration.domain.user.credential;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Credentials extends ValueObject {
  private final String username;
  private final String password;

  public Credentials(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }
}
