package ca.ulaval.glo4003.gateway.domain.user.token;

public interface TokenTranslator {
  Token encodeToken(TokenUser tokenUser);

  TokenUser decodeToken(Token token);
}
