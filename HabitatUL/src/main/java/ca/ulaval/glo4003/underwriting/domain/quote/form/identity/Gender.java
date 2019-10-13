package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import java.util.HashMap;
import java.util.Map;

public enum Gender {
  MALE,
  FEMALE,
  OTHER;

  private static final Map<String, Gender> LOOKUP_MAP = new HashMap<>();

  static {
    for (Gender gender : values()) {
      LOOKUP_MAP.put(gender.toString().toLowerCase(), gender);
    }
  }

  public static Gender getEnum(String rawValue) {
    return LOOKUP_MAP.getOrDefault(rawValue.toLowerCase(), OTHER);
  }
}
