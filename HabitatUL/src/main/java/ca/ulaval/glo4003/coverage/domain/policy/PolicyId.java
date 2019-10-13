package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class PolicyId extends ValueObject {
  private UUID value;

  public PolicyId() {
    this(UUID.randomUUID());
  }

  public PolicyId(String value) {
    this(UUID.fromString(value));
  }

  private PolicyId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }

  public String toRepresentation() {
    return value.toString();
  }
}
