package ca.ulaval.glo4003.administration.domain.user.token;

import ca.ulaval.glo4003.shared.domain.ValueComparableObject;

import java.time.Instant;

public class TokenPayload extends ValueComparableObject {
  private String userKey;
  private String username;
  private Instant expiration;

  public TokenPayload(String userKey, String username, Instant expiration) {
    this.userKey = userKey;
    this.username = username;
    this.expiration = expiration;
  }

  public String getUserKey() {
    return userKey;
  }

  public String getUsername() {
    return username;
  }

  public Instant getExpiration() {
    return expiration;
  }
}
