package ca.ulaval.glo4003.gateway.domain.user;

public class User {
  private UserId userId;
  private String username;

  public User(UserId userId, String username) {
    this.userId = userId;
    this.username = username;
  }

  public UserId getUserId() {
    return userId;
  }

  public String getUsername() {
    return username;
  }
}
