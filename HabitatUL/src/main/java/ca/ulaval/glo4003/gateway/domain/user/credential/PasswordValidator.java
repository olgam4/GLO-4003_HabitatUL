package ca.ulaval.glo4003.gateway.domain.user.credential;

public interface PasswordValidator {
  void registerPassword(String userKey, String password);

  boolean validatePassword(String userKey, String password);
}
