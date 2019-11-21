package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class PolicyRenewalId extends ValueObject {
  private final UUID value;

  public PolicyRenewalId() {
    this(UUID.randomUUID());
  }

  public PolicyRenewalId(String value) {
    this(UUID.fromString(value));
  }

  private PolicyRenewalId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }

  public String toRepresentation() {
    return value.toString();
  }
}
