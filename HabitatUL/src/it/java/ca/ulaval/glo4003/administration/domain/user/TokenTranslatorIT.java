package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.token.InvalidTokenSignatureError;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.generator.user.TokenPayloadGenerator;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class TokenTranslatorIT {
  private static final TokenPayload TOKEN_PAYLOAD = TokenPayloadGenerator.createValidTokenPayload();
  private static final Token AN_INVALID_TOKEN = new Token(Faker.instance().internet().uuid());

  private TokenTranslator subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void decodingToken_withValidToken_shouldRetrieveEncodedToken() {
    Token token = subject.encodeToken(TOKEN_PAYLOAD);

    TokenPayload tokenPayload = subject.decodeToken(token);

    assertEquals(TOKEN_PAYLOAD, tokenPayload);
  }

  @Test(expected = InvalidTokenSignatureError.class)
  public void decodingToken_withInvalidToken_shouldThrow() {
    subject.decodeToken(AN_INVALID_TOKEN);
  }

  protected abstract TokenTranslator createSubject();
}