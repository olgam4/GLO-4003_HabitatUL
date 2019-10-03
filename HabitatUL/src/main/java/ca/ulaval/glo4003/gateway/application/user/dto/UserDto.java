package ca.ulaval.glo4003.gateway.application.user.dto;

import ca.ulaval.glo4003.gateway.domain.user.UserId;

public class UserDto {
  private UserId userId;
  private String username;

  public UserDto(UserId userId, String username) {
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
