package ca.ulaval.glo4003.management.infrastructure.user;

import ca.ulaval.glo4003.management.domain.user.exception.InvalidTokenException;
import ca.ulaval.glo4003.management.domain.user.token.Token;
import ca.ulaval.glo4003.management.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.management.domain.user.token.TokenTranslator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtTokenTranslator implements TokenTranslator {
  private static final String ISSUER = "habitat-ul";
  private Algorithm signingAlgorithm;
  private JWTVerifier verifier;

  public JwtTokenTranslator(String secret) {
    this(Algorithm.HMAC256(secret));
  }

  public JwtTokenTranslator(Algorithm signingAlgorithm) {
    this(signingAlgorithm, JWT.require(signingAlgorithm).withIssuer(ISSUER).build());
  }

  public JwtTokenTranslator(Algorithm signingAlgorithm, JWTVerifier verifier) {
    this.signingAlgorithm = signingAlgorithm;
    this.verifier = verifier;
  }

  @Override
  public Token encodeToken(TokenPayload tokenPayload) {
    String token =
        JWT.create()
            .withIssuer(ISSUER)
            .withClaim("userId", tokenPayload.getUserKey())
            .withClaim("username", tokenPayload.getUsername())
            .sign(signingAlgorithm);
    return new Token(token);
  }

  @Override
  public TokenPayload decodeToken(Token token) {
    try {
      DecodedJWT jwt = verifier.verify(token.getValue());
      String userKey = jwt.getClaim("userId").asString();
      String username = jwt.getClaim("username").asString();
      return new TokenPayload(userKey, username);
    } catch (JWTVerificationException exception) {
      throw new InvalidTokenException();
    }
  }
}
