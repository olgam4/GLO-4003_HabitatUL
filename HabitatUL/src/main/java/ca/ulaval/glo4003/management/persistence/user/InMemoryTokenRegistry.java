package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.TokenRegistry;
import ca.ulaval.glo4003.management.domain.user.exception.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTokenRegistry implements TokenRegistry {
  private Map<String, String> tokenByUserKey = new HashMap<>();
  private Map<String, String> userKeyByToken = new HashMap<>();

  @Override
  public void register(String userKey, String tokenKey) {
    userKeyByToken.put(tokenKey, userKey);
    tokenByUserKey.put(userKey, tokenKey);
  }

  @Override
  public String getUserKey(String token) {
    return userKeyByToken.computeIfAbsent(
        token,
        (String ignored) -> {
          throw new KeyNotFoundException(token);
        });
  }

  @Override
  public String getToken(String userKey) {
    return tokenByUserKey.computeIfAbsent(
        userKey,
        (String ignored) -> {
          throw new KeyNotFoundException(userKey);
        });
  }
}
