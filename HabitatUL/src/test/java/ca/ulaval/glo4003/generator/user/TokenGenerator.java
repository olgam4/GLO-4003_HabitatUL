package ca.ulaval.glo4003.generator.user;

import ca.ulaval.glo4003.management.domain.user.token.Token;
import com.github.javafaker.Faker;

public class TokenGenerator {
  private static final String JWT_REGEX = "[A-Za-z0-9-_=]+.[A-Za-z0-9-_=]+.?[A-Za-z0-9-_.+/=]*";

  public static Token createToken() {
    Faker faker = Faker.instance();
    String jwtString = faker.regexify(JWT_REGEX);
    return new Token(jwtString);
  }
}
