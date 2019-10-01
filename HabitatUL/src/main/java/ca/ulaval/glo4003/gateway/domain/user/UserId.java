package ca.ulaval.glo4003.gateway.domain.user;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.UUID;

public class UserId extends ValueObject {
  private UUID value;

  public UserId() {
    this(UUID.randomUUID());
  }

  public UserId(String value) {
    this(UUID.fromString(value));
  }

  private UserId(UUID value) {
    this.value = value;
  }

  public UUID getValue() {
    return value;
  }
}
