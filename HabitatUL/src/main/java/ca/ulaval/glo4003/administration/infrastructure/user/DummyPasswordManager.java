package ca.ulaval.glo4003.administration.infrastructure.user;

import ca.ulaval.glo4003.administration.domain.user.credential.InvalidCredentialsException;
import ca.ulaval.glo4003.administration.domain.user.credential.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordManager;

import java.util.HashMap;
import java.util.Map;

public class DummyPasswordManager implements PasswordManager {
  private final Map<String, String> passwords = new HashMap<>();

  @Override
  public void registerPassword(String userKey, String password) throws InvalidPasswordException {
    if (password == null) throw new InvalidPasswordException(null, "NULL PASSWORD");

    passwords.put(userKey, password);
  }

  @Override
  public void validatePassword(String userKey, String password) throws InvalidCredentialsException {
    String storedPassword = passwords.get(userKey);

    if (password == null || !password.equals(storedPassword)) {
      throw new InvalidCredentialsException();
    }
  }
}
