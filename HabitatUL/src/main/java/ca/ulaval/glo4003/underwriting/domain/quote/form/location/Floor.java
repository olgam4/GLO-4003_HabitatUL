package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

public class Floor extends ValueObject {
  private final int value;

  public Floor(String value, FloorFormatter floorFormatter) throws InvalidArgumentException {
    this.value = floorFormatter.format(value);
  }

  public int getValue() {
    return value;
  }
}
