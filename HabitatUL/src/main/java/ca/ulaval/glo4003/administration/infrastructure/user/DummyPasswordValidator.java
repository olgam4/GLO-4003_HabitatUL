package ca.ulaval.glo4003.administration.infrastructure.user;

import ca.ulaval.glo4003.administration.domain.user.credential.InvalidPasswordException;
import ca.ulaval.glo4003.administration.domain.user.credential.PasswordValidator;

import java.util.HashMap;
import java.util.Map;

public class DummyPasswordValidator implements PasswordValidator {
  private final Map<String, String> passwords = new HashMap<>();

  @Override
  public void registerPassword(String userKey, String password) throws InvalidPasswordException {
    if (password == null) throw new InvalidPasswordException(null, "NULL PASSWORD");

    passwords.put(userKey, password);
  }

  @Override
  public boolean validatePassword(String userKey, String password) {
    String storedPassword = passwords.get(userKey);
    return password != null && password.equals(storedPassword);
  }
}
