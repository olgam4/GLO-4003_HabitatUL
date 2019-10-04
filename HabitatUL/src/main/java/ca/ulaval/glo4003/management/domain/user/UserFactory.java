package ca.ulaval.glo4003.management.domain.user;

public class UserFactory {
  public User create(String username) {
    UserId userId = new UserId();
    return new User(userId, username);
  }
}
