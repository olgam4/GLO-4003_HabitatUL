package ca.ulaval.glo4003.generator.user;

import ca.ulaval.glo4003.management.domain.user.token.TokenPayload;
import com.github.javafaker.Faker;

public class TokenPayloadGenerator {
  public static TokenPayload createTokenPayload() {
    Faker faker = Faker.instance();
    return new TokenPayload(faker.internet().uuid(), faker.name().username());
  }
}
