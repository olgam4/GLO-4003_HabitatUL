package ca.ulaval.glo4003.generator.user;

import ca.ulaval.glo4003.management.domain.user.token.Token;
import com.github.javafaker.Faker;

public class TokenGenerator {
  public static Token createToken() {
    Faker faker = Faker.instance();
    return new Token(faker.internet().uuid());
  }
}
