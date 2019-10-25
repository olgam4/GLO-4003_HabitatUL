package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class QuoteId extends ValueObject {
  private final UUID value;

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

  public String toRepresentation() {
    return value.toString();
  }
}
