package ca.ulaval.glo4003.generator.user;

import ca.ulaval.glo4003.gateway.domain.user.User;
import ca.ulaval.glo4003.gateway.domain.user.UserId;
import com.github.javafaker.Faker;

public class UserGenerator {
  private UserGenerator() {}

  public static User createValidUser() {
    return createValidUserWithId(createUserId());
  }

  public static User createValidUserWithId(UserId userId) {
    String name = Faker.instance().name().username();
    return new User(userId, name);
  }

  private static UserId createUserId() {
    return new UserId();
  }
}
