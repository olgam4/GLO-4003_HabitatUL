package ca.ulaval.glo4003.gateway.presentation.user;

import ca.ulaval.glo4003.gateway.domain.user.token.Token;
import ca.ulaval.glo4003.gateway.presentation.user.response.AuthenticationResponse;

public class UserViewAssembler {
  public AuthenticationResponse from(Token token) {
    return new AuthenticationResponse(token);
  }
}