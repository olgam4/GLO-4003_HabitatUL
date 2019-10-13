package ca.ulaval.glo4003.generator.user;

import ca.ulaval.glo4003.administration.domain.user.credential.Credentials;
import com.github.javafaker.Faker;

public class CredentialsGenerator {
  public static Credentials createCredentials() {
    Faker faker = Faker.instance();
    return new Credentials(faker.name().username(), faker.internet().uuid());
  }
}
