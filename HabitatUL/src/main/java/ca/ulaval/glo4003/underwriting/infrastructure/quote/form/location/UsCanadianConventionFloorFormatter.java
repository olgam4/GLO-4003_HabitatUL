package ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.FloorFormatter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UsCanadianConventionFloorFormatter implements FloorFormatter {
  private static final String HIGHER_FLOOR_ABBR = "TH";
  private static final String LOWER_FLOOR_ABBR = "B";
  private static final Map<String, Integer> ABBR_VALUE_MAP = new HashMap<>();

  static {
    ABBR_VALUE_MAP.put("3RD", 3);
    ABBR_VALUE_MAP.put("2ND", 2);
    ABBR_VALUE_MAP.put("1ST", 1);
    ABBR_VALUE_MAP.put("L", 1);
    ABBR_VALUE_MAP.put("G", 1);
    ABBR_VALUE_MAP.put("RC", 1);
    ABBR_VALUE_MAP.put("LL", 0);
  }

  @Override
  public int format(String floorValue) throws InvalidArgumentException {
    return parse(floorValue).orElseThrow(InvalidArgumentException::new);
  }

  private Optional<Integer> parse(String value) {
    if (value.endsWith(HIGHER_FLOOR_ABBR)) {
      return parseHigherFloor(value);
    } else if (value.startsWith(LOWER_FLOOR_ABBR)) {
      return parseLowerFloor(value);
    } else {
      return Optional.ofNullable(ABBR_VALUE_MAP.get(value));
    }
  }

  private Optional<Integer> parseHigherFloor(String value) {
    int parsedFloorNumber;

    try {
      String floorValue = value.substring(0, value.length() - HIGHER_FLOOR_ABBR.length());
      parsedFloorNumber = Integer.parseInt(floorValue);
      if (parsedFloorNumber <= 3) return Optional.empty();
    } catch (NumberFormatException exception) {
      return Optional.empty();
    }

    return Optional.of(parsedFloorNumber);
  }

  private Optional<Integer> parseLowerFloor(String value) {
    int parsedFloorNumber;

    try {
      String floorValue = value.substring(LOWER_FLOOR_ABBR.length());
      parsedFloorNumber = Integer.parseInt(floorValue);
      if (parsedFloorNumber < 1) return Optional.empty();
    } catch (NumberFormatException exception) {
      return Optional.empty();
    }

    return Optional.of(-parsedFloorNumber);
  }
}
