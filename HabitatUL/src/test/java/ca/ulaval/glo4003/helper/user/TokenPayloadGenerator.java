package ca.ulaval.glo4003.helper.user;

import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import com.github.javafaker.Faker;

import java.util.concurrent.TimeUnit;

public class TokenPayloadGenerator {
  private TokenPayloadGenerator() {}

  public static TokenPayload createValidTokenPayload() {
    Faker faker = Faker.instance();
    return new TokenPayload(
        faker.internet().uuid(),
        faker.name().username(),
        faker.date().future(3, TimeUnit.DAYS).toInstant());
  }

  public static TokenPayload createExpiredTokenPayload() {
    Faker faker = Faker.instance();
    return new TokenPayload(
        faker.internet().uuid(),
        faker.name().username(),
        faker.date().past(3, TimeUnit.DAYS).toInstant());
  }
}
