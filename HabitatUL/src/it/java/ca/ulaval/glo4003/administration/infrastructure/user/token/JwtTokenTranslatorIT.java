package ca.ulaval.glo4003.administration.infrastructure.user.token;

import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslatorIT;
import com.github.javafaker.Faker;

public class JwtTokenTranslatorIT extends TokenTranslatorIT {
  @Override
  protected TokenTranslator createSubject() {
    return new JwtTokenTranslator(Faker.instance().internet().uuid());
  }
}
