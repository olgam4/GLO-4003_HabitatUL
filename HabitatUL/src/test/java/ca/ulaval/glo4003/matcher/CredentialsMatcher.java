package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.gateway.presentation.administration.user.request.CredentialsRequest;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class CredentialsMatcher {
  private CredentialsMatcher() {}

  public static Matcher<Credentials> matchesCredentials(
      final CredentialsRequest credentialsRequest) {
    return allOf(
        hasProperty("username", equalTo(credentialsRequest.getUsername())),
        hasProperty("password", equalTo(credentialsRequest.getPassword())));
  }
}
