package ca.ulaval.glo4003.coverage.domain.policy;

import java.util.UUID;

public class PolicyId {
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
}
