package ca.ulaval.glo4003.gateway.domain.user;

public class UserFactory {
  public User create(String username) {
    UserId userId = new UserId();
    return new User(userId, username);
  }
}
