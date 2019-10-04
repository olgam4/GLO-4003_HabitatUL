package ca.ulaval.glo4003.management.domain.user.token;

public interface TokenTranslator {
  Token encodeToken(TokenPayload tokenPayload);

  TokenPayload decodeToken(Token token);
}
