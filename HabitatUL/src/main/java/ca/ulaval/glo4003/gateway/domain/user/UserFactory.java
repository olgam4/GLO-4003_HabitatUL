package ca.ulaval.glo4003.gateway.domain.user;

public class UserFactory {
  private PasswordValidator passwordValidator;

  public UserFactory(PasswordValidator passwordValidator) {
    this.passwordValidator = passwordValidator;
  }

  public User create(String username, String password) {
    UserId userId = new UserId();
    String userKey = userId.getValue().toString();
    passwordValidator.registerPassword(userKey, password);
    return new User(userId, username);
  }
}
