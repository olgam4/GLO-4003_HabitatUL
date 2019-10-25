package ca.ulaval.glo4003.administration.persistence.user;

import ca.ulaval.glo4003.administration.domain.user.UsernameRegistry;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryUsernameRegistry implements UsernameRegistry {
  private Map<String, String> usernameByUserKey = new HashMap<>();
  private Map<String, String> userKeyByUsername = new HashMap<>();

  @Override
  public void register(String userKey, String username) throws KeyAlreadyExistException {
    checkIfUserKeyAlreadyExist(userKey);
    checkIfUsernameAlreadyExist(username);
    userKeyByUsername.put(username, userKey);
    usernameByUserKey.put(userKey, username);
  }

  private void checkIfUserKeyAlreadyExist(String userKey) throws KeyAlreadyExistException {
    if (usernameByUserKey.containsKey(userKey)) {
      throw new KeyAlreadyExistException();
    }
  }

  private void checkIfUsernameAlreadyExist(String username) throws KeyAlreadyExistException {
    if (userKeyByUsername.containsKey(username)) {
      throw new KeyAlreadyExistException();
    }
  }

  @Override
  public String getUserKey(String username) throws KeyNotFoundException {
    return Optional.ofNullable(userKeyByUsername.get(username))
        .orElseThrow(KeyNotFoundException::new);
  }

  @Override
  public String getUsername(String userKey) throws KeyNotFoundException {
    return Optional.ofNullable(usernameByUserKey.get(userKey))
        .orElseThrow(KeyNotFoundException::new);
  }
}
