package ca.ulaval.glo4003.management.application.user;

import ca.ulaval.glo4003.management.application.user.dto.UserDto;
import ca.ulaval.glo4003.management.domain.user.User;

public class UserAssembler {
  public User from(UserDto userDto) {
    return new User(userDto.getUserId(), userDto.getUsername());
  }

  public UserDto from(User user) {
    return new UserDto(user.getUserId(), user.getUsername());
  }
}
