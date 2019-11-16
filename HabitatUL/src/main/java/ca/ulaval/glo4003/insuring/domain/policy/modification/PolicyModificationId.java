package ca.ulaval.glo4003.insuring.domain.policy.modification;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class PolicyModificationId extends ValueObject {
  private final UUID value;

  public PolicyModificationId() {
    this(UUID.randomUUID());
  }

  public PolicyModificationId(String value) {
    this(UUID.fromString(value));
  }

  private PolicyModificationId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }

  public String toRepresentation() {
    return value.toString();
  }
}
