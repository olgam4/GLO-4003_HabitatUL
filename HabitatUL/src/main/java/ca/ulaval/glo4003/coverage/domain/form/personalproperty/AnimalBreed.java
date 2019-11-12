package ca.ulaval.glo4003.coverage.domain.form.personalproperty;

import java.util.HashMap;
import java.util.Map;

public enum AnimalBreed {
  CAT,
  DOG,
  GOLD_FISH,
  SNAKE,
  OTHER;

  private static final Map<String, AnimalBreed> LOOKUP_MAP = new HashMap<>();

  static {
    for (AnimalBreed animalBreed : values()) {
      LOOKUP_MAP.put(animalBreed.toString().toLowerCase(), animalBreed);
    }
  }

  public static AnimalBreed getEnum(String rawValue) {
    return LOOKUP_MAP.getOrDefault(rawValue.toLowerCase(), OTHER);
  }
}
