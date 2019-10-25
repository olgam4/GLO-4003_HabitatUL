package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;

public interface UsernameRegistry {
  void register(String userKey, String username) throws KeyAlreadyExistException;

  String getUserKey(String username) throws KeyNotFoundException;

  String getUsername(String userKey) throws KeyNotFoundException;
}
