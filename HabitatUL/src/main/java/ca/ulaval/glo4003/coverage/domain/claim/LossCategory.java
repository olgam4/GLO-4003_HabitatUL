package ca.ulaval.glo4003.coverage.domain.claim;

import java.util.HashMap;
import java.util.Map;

public enum LossCategory {
  BICYCLE,
  CLOTHES,
  COMPUTER_EQUIPMENT,
  ELECTRONICS,
  FURNITURE_AND_HOUSEHOLD,
  OTHER;

  private static final Map<String, LossCategory> LOOKUP_MAP = new HashMap<>();

  static {
    for (LossCategory lossCategory : values()) {
      LOOKUP_MAP.put(lossCategory.toString().toLowerCase(), lossCategory);
    }
  }

  public static LossCategory getEnum(String rawValue) {
    return LOOKUP_MAP.getOrDefault(rawValue.toLowerCase(), OTHER);
  }
}
