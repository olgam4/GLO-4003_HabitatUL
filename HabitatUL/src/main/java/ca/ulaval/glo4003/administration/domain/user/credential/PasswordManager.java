package ca.ulaval.glo4003.administration.domain.user.credential;

public interface PasswordManager {
  void registerPassword(String userKey, String password) throws InvalidPasswordException;

  void validatePassword(String userKey, String password) throws InvalidCredentialsException;
}
