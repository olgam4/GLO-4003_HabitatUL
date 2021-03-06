package ca.ulaval.glo4003.gateway.presentation.administration.user;

import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.administration.domain.user.token.Token;
import ca.ulaval.glo4003.gateway.presentation.administration.user.request.CredentialsRequest;
import ca.ulaval.glo4003.gateway.presentation.administration.user.response.AuthenticationResponse;

public class UserViewAssembler {
  public Credentials from(CredentialsRequest credentialsRequest) {
    return new Credentials(credentialsRequest.getUsername(), credentialsRequest.getPassword());
  }

  public AuthenticationResponse from(Token token) {
    return new AuthenticationResponse(token);
  }
}
