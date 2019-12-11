package ca.ulaval.glo4003.shared.domain.identity;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Cycle {
  FIRST_CYCLE("1er"),
  SECOND_CYCLE("2e"),
  THIRD_CYCLE("3e");

  private static final Map<String, Cycle> LOOKUP_MAP = new HashMap<>();

  static {
    for (Cycle cycle : values()) {
      LOOKUP_MAP.put(cycle.value.toLowerCase(), cycle);
    }
  }

  private final String value;

  Cycle(final String value) {
    this.value = value;
  }

  public static Cycle getEnum(String rawValue) throws InvalidArgumentException {
    return Optional.ofNullable(LOOKUP_MAP.get(rawValue.toLowerCase()))
        .orElseThrow(InvalidArgumentException::new);
  }

  public String getValue() {
    return value;
  }
}
