package ca.ulaval.glo4003.administration.infrastructure.user;

import ca.ulaval.glo4003.administration.domain.user.TokenTranslatorIT;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import com.github.javafaker.Faker;

public class JwtTokenTranslatorIT extends TokenTranslatorIT {
  @Override
  protected TokenTranslator createSubject() {
    return new JwtTokenTranslator(Faker.instance().internet().uuid());
  }
}
