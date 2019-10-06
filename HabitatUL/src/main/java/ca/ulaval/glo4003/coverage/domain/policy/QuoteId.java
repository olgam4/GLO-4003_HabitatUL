package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class QuoteId extends ValueObject {
  private UUID value;

  public QuoteId() {
    this(UUID.randomUUID());
  }

  public QuoteId(String value) {
    this(UUID.fromString(value));
  }

  private QuoteId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }
}
