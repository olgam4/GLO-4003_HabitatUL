package ca.ulaval.glo4003.gateway.presentation.user;

import ca.ulaval.glo4003.gateway.presentation.user.response.AuthenticationResponse;
import ca.ulaval.glo4003.management.domain.user.token.Token;

public class UserViewAssembler {
  public AuthenticationResponse from(Token token) {
    return new AuthenticationResponse(token);
  }
}
