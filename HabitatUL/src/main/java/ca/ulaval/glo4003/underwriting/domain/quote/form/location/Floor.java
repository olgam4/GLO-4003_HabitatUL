package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Floor extends ValueObject {
  private static final String BASEMENT_ABBR = "SS";
  private static final String GROUND_FLOOR_ABBR = "RC";
  private static final String FIRST_FLOOR_ABBR = "1ST";
  private static final String SECOND_FLOOR_ABBR = "2ND";
  private static final String THIRD_FLOOR_ABBR = "3RD";
  private static final String HIGHER_FLOOR_ABBR = "TH";
  private static final Map<String, Integer> ABBR_VALUE_MAP = new HashMap<String, Integer>();

  static {
    ABBR_VALUE_MAP.put(GROUND_FLOOR_ABBR, 0);
    ABBR_VALUE_MAP.put(FIRST_FLOOR_ABBR, 1);
    ABBR_VALUE_MAP.put(SECOND_FLOOR_ABBR, 2);
    ABBR_VALUE_MAP.put(THIRD_FLOOR_ABBR, 3);
  }

  private String value;
  private int floorNumber;

  public Floor(String value) throws InvalidFloorException {
    this.value = value;
    Optional<Integer> parsedFloorNumber = parse(value);
    this.floorNumber =
        parsedFloorNumber.orElseThrow(() -> new InvalidFloorException(value)).intValue();
  }

  private Optional<Integer> parse(String value) {
    if (value.startsWith(BASEMENT_ABBR)) {
      int parsedFloorNumber;
      try {
        parsedFloorNumber = Integer.parseInt(value.substring(2));

        if (parsedFloorNumber < 1) {
          return Optional.empty();
        }
      } catch (NumberFormatException exception) {
        return Optional.empty();
      }

      return Optional.of(-parsedFloorNumber);

    } else if (value.endsWith(HIGHER_FLOOR_ABBR)) {
      int parsedFloorNumber;
      try {
        parsedFloorNumber = Integer.parseInt(value.substring(0, value.length() - 2));
      } catch (NumberFormatException exception) {
        return Optional.empty();
      }

      if (parsedFloorNumber <= 3) {
        return Optional.empty();
      }
      return Optional.of(parsedFloorNumber);

    } else {
      return Optional.ofNullable(ABBR_VALUE_MAP.get(value));
    }
  }

  public String getValue() {
    return value;
  }

  public int getFloorNumber() {
    return floorNumber;
  }
}
