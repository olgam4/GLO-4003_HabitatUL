package ca.ulaval.glo4003.coverage.domain.claim;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class ClaimId extends ValueObject {
  private final UUID value;

  public ClaimId() {
    this(UUID.randomUUID());
  }

  public ClaimId(String value) {
    this(UUID.fromString(value));
  }

  private ClaimId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }

  public String toRepresentation() {
    return value.toString();
  }
}
