package ca.ulaval.glo4003.coverage.domain.claim;

import java.util.HashMap;
import java.util.Map;

public enum SinisterType {
  FIRE,
  THEFT;

  private static final Map<String, SinisterType> LOOKUP_MAP = new HashMap<>();

  static {
    for (SinisterType sinisterType : values()) {
      LOOKUP_MAP.put(sinisterType.toString().toLowerCase(), sinisterType);
    }
  }

  public static SinisterType getEnum(String rawValue) {
    return LOOKUP_MAP.get(rawValue.toLowerCase());
  }
}
