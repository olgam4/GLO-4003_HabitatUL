package ca.ulaval.glo4003.management.domain.user;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class QuoteKey extends ValueObject {
  private String value;

  public QuoteKey(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
