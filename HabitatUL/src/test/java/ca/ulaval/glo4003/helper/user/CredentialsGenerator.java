package ca.ulaval.glo4003.helper.user;

import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import ca.ulaval.glo4003.gateway.presentation.user.request.CredentialsRequest;
import com.github.javafaker.Faker;

public class CredentialsGenerator {
  public static CredentialsRequest createCredentialsRequest() {
    return new CredentialsRequest(createUsername(), createPassword());
  }

  public static Credentials createCredentials() {
    return new Credentials(createUsername(), createPassword());
  }

  public static String createUsername() {
    return Faker.instance().name().username();
  }

  public static String createPassword() {
    return Faker.instance().internet().uuid();
  }
}
