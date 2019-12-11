package ca.ulaval.glo4003.shared.domain.identity;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum Degree {
  BACHELOR("baccalaureat"),
  MASTER("maitrise"),
  DOCTORAL("doctorat");

  private static final Map<String, Degree> LOOKUP_MAP = new HashMap<>();

  static {
    for (Degree degree : values()) {
      LOOKUP_MAP.put(degree.value.toLowerCase(), degree);
    }
  }

  private final String value;

  Degree(final String value) {
    this.value = value;
  }

  public static Degree getEnum(String rawValue) throws InvalidArgumentException {
    return Optional.ofNullable(LOOKUP_MAP.get(rawValue.toLowerCase()))
        .orElseThrow(InvalidArgumentException::new);
  }

  public String getValue() {
    return value;
  }
}
