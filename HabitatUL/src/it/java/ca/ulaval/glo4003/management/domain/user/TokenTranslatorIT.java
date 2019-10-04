package ca.ulaval.glo4003.management.domain.user;

import ca.ulaval.glo4003.generator.user.UserGenerator;
import ca.ulaval.glo4003.management.domain.user.exception.InvalidTokenException;
import ca.ulaval.glo4003.management.domain.user.token.Token;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.management.domain.user.token.TokenUser;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TokenTranslatorIT {
  private static final TokenUser TOKEN_USER = UserGenerator.createTokenUser();
  private static final Token AN_INVALID_TOKEN = new Token(Faker.instance().internet().uuid());

  private TokenTranslator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test(expected = InvalidTokenException.class)
  public void decodingToken_withInvalidToken_shouldThrow() {
    subject.decodeToken(AN_INVALID_TOKEN);
  }

  @Test
  public void decodingToken_withValidToken_shouldRetrieveEncodedUser() {
    Token token = subject.encodeToken(TOKEN_USER);

    TokenUser tokenUser = subject.decodeToken(token);

    assertEquals(tokenUser, TOKEN_USER);
  }

  protected abstract TokenTranslator createSubject();
}
