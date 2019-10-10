package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.TokenRegistry;
import ca.ulaval.glo4003.management.domain.user.exception.KeyAlreadyExistError;
import ca.ulaval.glo4003.management.domain.user.exception.KeyNotFoundError;

import java.util.HashMap;
import java.util.Map;

public class InMemoryTokenRegistry implements TokenRegistry {
  private Map<String, String> tokenByUserKey = new HashMap<>();
  private Map<String, String> userKeyByToken = new HashMap<>();

  @Override
  public void register(String userKey, String token) {
    checkIfUserKeyAlreadyExist(userKey);
    checkIfTokenAlreadyExist(token);
    userKeyByToken.put(token, userKey);
    tokenByUserKey.put(userKey, token);
  }

  private void checkIfUserKeyAlreadyExist(String userKey) {
    if (tokenByUserKey.containsKey(userKey)) {
      throw new KeyAlreadyExistError(userKey);
    }
  }

  private void checkIfTokenAlreadyExist(String token) {
    if (userKeyByToken.containsKey(token)) {
      throw new KeyAlreadyExistError(token);
    }
  }

  @Override
  public String getUserKey(String token) {
    return userKeyByToken.computeIfAbsent(
        token,
        (String ignored) -> {
          throw new KeyNotFoundError(token);
        });
  }

  @Override
  public String getToken(String userKey) {
    return tokenByUserKey.computeIfAbsent(
        userKey,
        (String ignored) -> {
          throw new KeyNotFoundError(userKey);
        });
  }
}
