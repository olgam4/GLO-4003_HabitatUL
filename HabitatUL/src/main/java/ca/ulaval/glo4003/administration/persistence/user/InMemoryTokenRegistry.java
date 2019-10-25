package ca.ulaval.glo4003.administration.persistence.user;

import ca.ulaval.glo4003.administration.domain.user.TokenRegistry;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryTokenRegistry implements TokenRegistry {
  private Map<String, String> tokenByUserKey = new HashMap<>();
  private Map<String, String> userKeyByToken = new HashMap<>();

  @Override
  public void register(String userKey, String token) throws KeyAlreadyExistException {
    checkIfUserKeyAlreadyExist(userKey);
    checkIfTokenAlreadyExist(token);
    userKeyByToken.put(token, userKey);
    tokenByUserKey.put(userKey, token);
  }

  private void checkIfUserKeyAlreadyExist(String userKey) throws KeyAlreadyExistException {
    if (tokenByUserKey.containsKey(userKey)) {
      throw new KeyAlreadyExistException();
    }
  }

  private void checkIfTokenAlreadyExist(String token) throws KeyAlreadyExistException {
    if (userKeyByToken.containsKey(token)) {
      throw new KeyAlreadyExistException();
    }
  }

  @Override
  public String getUserKey(String token) throws KeyNotFoundException {
    return Optional.ofNullable(userKeyByToken.get(token)).orElseThrow(KeyNotFoundException::new);
  }

  @Override
  public String getToken(String userKey) throws KeyNotFoundException {
    return Optional.ofNullable(tokenByUserKey.get(userKey)).orElseThrow(KeyNotFoundException::new);
  }
}
