package ca.ulaval.glo4003.gateway.infrastructure.user;

import ca.ulaval.glo4003.gateway.domain.user.UserId;
import ca.ulaval.glo4003.gateway.domain.user.exception.InvalidTokenException;
import ca.ulaval.glo4003.gateway.domain.user.token.Token;
import ca.ulaval.glo4003.gateway.domain.user.token.TokenTranslator;
import ca.ulaval.glo4003.gateway.domain.user.token.TokenUser;
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
  public Token encodeToken(TokenUser tokenUser) {
    String token =
        JWT.create()
            .withIssuer(ISSUER)
            .withClaim("userId", tokenUser.getUserId().getValue().toString())
            .withClaim("username", tokenUser.getUsername())
            .sign(signingAlgorithm);
    return new Token(token);
  }

  @Override
  public TokenUser decodeToken(Token token) {
    try {
      DecodedJWT jwt = verifier.verify(token.getValue());
      UserId userId = new UserId(jwt.getClaim("userId").asString());
      String username = jwt.getClaim("username").asString();
      return new TokenUser(userId, username);
    } catch (JWTVerificationException exception) {
      throw new InvalidTokenException();
    }
  }
}
