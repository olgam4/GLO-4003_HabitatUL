package ca.ulaval.glo4003.management.domain.user.token;

import ca.ulaval.glo4003.management.domain.user.UserId;
import ca.ulaval.glo4003.shared.domain.ValueObject;

public class TokenUser extends ValueObject {
  private UserId userId;
  private String username;

  public TokenUser(UserId userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }
}
