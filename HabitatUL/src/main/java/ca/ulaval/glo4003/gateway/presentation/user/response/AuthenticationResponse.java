package ca.ulaval.glo4003.gateway.presentation.user.response;

import ca.ulaval.glo4003.administration.domain.user.token.Token;

public class AuthenticationResponse {
  private Token accessToken;

  public AuthenticationResponse(Token accessToken) {
    this.accessToken = accessToken;
  }

  public Token getAccessToken() {
    return accessToken;
  }
}
