package ca.ulaval.glo4003.gateway.presentation.user;

import com.github.javafaker.Faker;
import org.json.JSONObject;

public class CredentialsBuilder {
  private final String DEFAULT_USERNAME = createUsername();
  private final String DEFAULT_PASSWORD = createPassword();

  private String username = DEFAULT_USERNAME;
  private String password = DEFAULT_PASSWORD;

  private String createUsername() {
    return Faker.instance().name().username();
  }

  private String createPassword() {
    return Faker.instance().internet().password();
  }

  private CredentialsBuilder() {}

  public static CredentialsBuilder aCredentialsRequest() {
    return new CredentialsBuilder();
  }

  public JSONObject build() {
    JSONObject json = new JSONObject();
    json.put("username", username);
    json.put("password", password);
    return json;
  }
}
