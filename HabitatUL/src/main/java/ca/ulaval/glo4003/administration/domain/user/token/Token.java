package ca.ulaval.glo4003.administration.domain.user.token;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Token extends ValueObject {
  private String token;

  public Token(String token) {
    this.token = token;
  }

  public String getValue() {
    return token;
  }
}
