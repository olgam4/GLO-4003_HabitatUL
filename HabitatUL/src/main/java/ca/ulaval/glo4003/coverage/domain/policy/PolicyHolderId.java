package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class PolicyHolderId extends ValueObject {
  private UUID value;

  public PolicyHolderId() {
    this(UUID.randomUUID());
  }

  public PolicyHolderId(String value) {
    this(UUID.fromString(value));
  }

  private PolicyHolderId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }
}
