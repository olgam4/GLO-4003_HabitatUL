package ca.ulaval.glo4003.underwriting.domain.quote.form.building;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum PreventionSystem {
  CENTRAL_ALARM,
  SPRINKLER;

  private static final Map<String, PreventionSystem> LOOKUP_MAP = new HashMap<>();

  static {
    for (PreventionSystem preventionSystem : values()) {
      LOOKUP_MAP.put(preventionSystem.toString().toLowerCase(), preventionSystem);
    }
  }

  public static PreventionSystem getEnum(String rawValue) throws InvalidArgumentException {
    return Optional.ofNullable(LOOKUP_MAP.get(rawValue.toLowerCase()))
        .orElseThrow(InvalidArgumentException::new);
  }
}
