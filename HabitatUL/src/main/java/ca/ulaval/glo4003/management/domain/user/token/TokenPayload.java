package ca.ulaval.glo4003.management.domain.user.token;

import ca.ulaval.glo4003.shared.domain.ValueComparableObject;

public class TokenPayload extends ValueComparableObject {
  private String userKey;
  private String username;

  public TokenPayload(String userKey, String username) {
    this.userKey = userKey;
    this.username = username;
  }

  public String getUserKey() {
    return userKey;
  }

  public String getUsername() {
    return username;
  }
}
