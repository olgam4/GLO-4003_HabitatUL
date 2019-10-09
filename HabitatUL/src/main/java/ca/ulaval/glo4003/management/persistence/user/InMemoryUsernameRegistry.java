package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.UsernameRegistry;
import ca.ulaval.glo4003.management.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.management.domain.user.exception.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUsernameRegistry implements UsernameRegistry {
  private Map<String, String> usernameByUserKey = new HashMap<>();
  private Map<String, String> userKeyByUsername = new HashMap<>();

  @Override
  public void register(String userKey, String username) {
    checkIfUserKeyAlreadyExist(userKey);
    checkIfUsernameAlreadyExist(username);
    userKeyByUsername.put(username, userKey);
    usernameByUserKey.put(userKey, username);
  }

  private void checkIfUserKeyAlreadyExist(String userKey) {
    if (usernameByUserKey.containsKey(userKey)) {
      throw new KeyAlreadyExistException(userKey);
    }
  }

  private void checkIfUsernameAlreadyExist(String username) {
    if (userKeyByUsername.containsKey(username)) {
      throw new KeyAlreadyExistException(username);
    }
  }

  @Override
  public String getUserKey(String username) {
    return userKeyByUsername.computeIfAbsent(
        username,
        (String newKey) -> {
          throw new KeyNotFoundException(username);
        });
  }

  @Override
  public String getUsername(String userKey) {
    return usernameByUserKey.computeIfAbsent(
        userKey,
        (String newKey) -> {
          throw new KeyNotFoundException(userKey);
        });
  }
}
