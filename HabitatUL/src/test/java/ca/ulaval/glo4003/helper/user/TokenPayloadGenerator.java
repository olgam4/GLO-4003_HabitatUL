package ca.ulaval.glo4003.helper.user;

import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import com.github.javafaker.Faker;

import java.time.Instant;

import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureInstant;

public class TokenPayloadGenerator {
  private TokenPayloadGenerator() {}

  public static TokenPayload createValidTokenPayload() {
    return new TokenPayload(createUserKey(), createUserName(), createExpiration());
  }

  public static String createUserKey() {
    return Faker.instance().internet().uuid();
  }

  public static String createUserName() {
    return Faker.instance().name().username();
  }

  public static Instant createExpiration() {
    return createFutureInstant();
  }
}
