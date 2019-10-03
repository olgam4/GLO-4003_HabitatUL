package ca.ulaval.glo4003.generator.user;

import ca.ulaval.glo4003.gateway.application.user.UserAssembler;
import ca.ulaval.glo4003.gateway.application.user.dto.UserDto;
import ca.ulaval.glo4003.gateway.domain.user.User;
import ca.ulaval.glo4003.gateway.domain.user.UserId;
import ca.ulaval.glo4003.gateway.domain.user.credential.Credentials;
import ca.ulaval.glo4003.gateway.domain.user.token.TokenUser;
import com.github.javafaker.Faker;

public class UserGenerator {
  private static UserAssembler userAssembler = new UserAssembler();

  private UserGenerator() {}

  public static User createUser() {
    return createUserWithId(createUserId());
  }

  public static User createUserWithId(UserId userId) {
    return new User(userId, createUsername());
  }

  public static UserDto createUserDto() {
    return userAssembler.from(createUser());
  }

  public static Credentials createCredentials() {
    return new Credentials(createUsername(), createPassword());
  }

  public static TokenUser createTokenUser() {
    return new TokenUser(new UserId(), createUsername());
  }

  private static UserId createUserId() {
    return new UserId();
  }

  private static String createUsername() {
    return Faker.instance().name().username();
  }

  private static String createPassword() {
    return Faker.instance().internet().uuid();
  }
}
