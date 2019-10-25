package ca.ulaval.glo4003.coverage.domain.claim;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum SinisterType {
  FIRE,
  THEFT;

  private static final Map<String, SinisterType> LOOKUP_MAP = new HashMap<>();

  static {
    for (SinisterType sinisterType : values()) {
      LOOKUP_MAP.put(sinisterType.toString().toLowerCase(), sinisterType);
    }
  }

  public static SinisterType getEnum(String rawValue) throws InvalidArgumentException {
    return Optional.ofNullable(LOOKUP_MAP.get(rawValue.toLowerCase()))
        .orElseThrow(InvalidArgumentException::new);
  }
}
