package ca.ulaval.glo4003.administration.helper.user;

import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;

import java.time.Instant;

import static ca.ulaval.glo4003.administration.helper.user.TokenPayloadGenerator.*;

public class TokenPayloadBuilder {
  private final String DEFAULT_USER_KEY = createUserKey();
  private final String DEFAULT_USER_NAME = createUserName();
  private final Instant DEFAULT_EXPIRATION = createExpiration();

  private String userKey = DEFAULT_USER_KEY;
  private String userName = DEFAULT_USER_NAME;
  private Instant expiration = DEFAULT_EXPIRATION;

  private TokenPayloadBuilder() {}

  public static TokenPayloadBuilder aTokenPayload() {
    return new TokenPayloadBuilder();
  }

  public TokenPayloadBuilder withUserKey(String userKey) {
    this.userKey = userKey;
    return this;
  }

  public TokenPayloadBuilder withUserName(String userName) {
    this.userName = userName;
    return this;
  }

  public TokenPayloadBuilder withExpiration(Instant expiration) {
    this.expiration = expiration;
    return this;
  }

  public TokenPayload build() {
    return new TokenPayload(userKey, userName, expiration);
  }
}
