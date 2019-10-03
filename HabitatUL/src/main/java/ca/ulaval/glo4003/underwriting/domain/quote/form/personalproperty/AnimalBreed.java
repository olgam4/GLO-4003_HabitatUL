package ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty;

import java.util.Arrays;

public enum AnimalBreed {
  CAT,
  DOG,
  FISH,
  SNAKE,
  OTHER;

  public static AnimalBreed getEnum(String rawValue) {
    return Arrays.stream(values())
        .filter(value -> value.name().equalsIgnoreCase(rawValue))
        .findFirst()
        .orElse(AnimalBreed.OTHER);
  }
}
