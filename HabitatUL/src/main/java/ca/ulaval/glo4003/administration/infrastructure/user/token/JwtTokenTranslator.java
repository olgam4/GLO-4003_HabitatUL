package ca.ulaval.glo4003.administration.infrastructure.user.token;

import ca.ulaval.glo4003.administration.domain.user.token.InvalidTokenSignatureException;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.administration.domain.user.token.TokenPayload;
import ca.ulaval.glo4003.administration.domain.user.token.TokenTranslator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.time.Instant;

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
            .withClaim("expiration", tokenPayload.getExpiration().toEpochMilli())
            .sign(signingAlgorithm);
    return new Token(token);
  }

  @Override
  public TokenPayload decodeToken(Token token) throws InvalidTokenSignatureException {
    try {
      DecodedJWT jwt = verifier.verify(token.getValue());
      String userKey = jwt.getClaim("userId").asString();
      String username = jwt.getClaim("username").asString();
      Instant expiration = Instant.ofEpochMilli(jwt.getClaim("expiration").asLong());
      return new TokenPayload(userKey, username, expiration);
    } catch (JWTVerificationException e) {
      throw new InvalidTokenSignatureException();
    }
  }
}
