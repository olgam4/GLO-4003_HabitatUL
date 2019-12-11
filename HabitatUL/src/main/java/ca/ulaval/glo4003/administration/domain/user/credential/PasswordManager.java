package ca.ulaval.glo4003.administration.domain.user.credential;

import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidCredentialsException;
import ca.ulaval.glo4003.administration.domain.user.credential.exception.InvalidPasswordException;

public interface PasswordManager {
  void registerPassword(String userKey, String password) throws InvalidPasswordException;

  void validatePassword(String userKey, String password) throws InvalidCredentialsException;
}
