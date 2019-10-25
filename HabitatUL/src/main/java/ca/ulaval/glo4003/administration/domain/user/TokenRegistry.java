package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.exception.KeyAlreadyExistException;
import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundException;

public interface TokenRegistry {
  void register(String userKey, String token) throws KeyAlreadyExistException;

  String getUserKey(String token) throws KeyNotFoundException;

  String getToken(String userKey) throws KeyNotFoundException;
}
