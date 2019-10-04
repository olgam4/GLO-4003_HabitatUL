package ca.ulaval.glo4003.management.domain.user.token;

public interface TokenTranslator {
  Token encodeToken(TokenUser tokenUser);

  TokenUser decodeToken(Token token);
}
