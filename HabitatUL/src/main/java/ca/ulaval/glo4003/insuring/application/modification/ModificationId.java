package ca.ulaval.glo4003.insuring.application.modification;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class ModificationId extends ValueObject {
  private final UUID value;

  public ModificationId() {
    this(UUID.randomUUID());
  }

  public ModificationId(String value) {
    this(UUID.fromString(value));
  }

  private ModificationId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }

  public String toRepresentation() {
    return value.toString();
  }
}
