package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.TokenRegistry;
import ca.ulaval.glo4003.management.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.management.domain.user.exception.KeyNotFoundException;

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
      throw new KeyAlreadyExistException(userKey);
    }
  }

  private void checkIfTokenAlreadyExist(String token) {
    if (userKeyByToken.containsKey(token)) {
      throw new KeyAlreadyExistException(token);
    }
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
