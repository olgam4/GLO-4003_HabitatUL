package ca.ulaval.glo4003.gateway.presentation.administration.user.request;

import javax.validation.constraints.NotNull;

public class CredentialsRequest {
  @NotNull private String username;
  @NotNull private String password;

  private CredentialsRequest() {}

  public CredentialsRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }
}
