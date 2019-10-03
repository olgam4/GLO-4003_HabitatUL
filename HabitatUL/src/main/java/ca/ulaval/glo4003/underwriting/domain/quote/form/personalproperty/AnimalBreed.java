package ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty;

import java.util.HashMap;
import java.util.Map;

public enum AnimalBreed {
  CAT,
  DOG,
  FISH,
  SNAKE,
  OTHER;
  private static final Map<String, AnimalBreed> LOOKUP_MAP = new HashMap<>();

  static {
    for (AnimalBreed breed : values()) {
      LOOKUP_MAP.put(breed.toString().toLowerCase(), breed);
    }
  }

  public static AnimalBreed getEnum(String rawValue) {
    return LOOKUP_MAP.getOrDefault(rawValue.toLowerCase(), OTHER);
  }
}
