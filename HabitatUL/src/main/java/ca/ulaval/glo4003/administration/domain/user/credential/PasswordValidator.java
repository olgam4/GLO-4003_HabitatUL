package ca.ulaval.glo4003.administration.domain.user.credential;

public interface PasswordValidator {
  void registerPassword(String userKey, String password) throws InvalidPasswordException;

  boolean validatePassword(String userKey, String password);
}
