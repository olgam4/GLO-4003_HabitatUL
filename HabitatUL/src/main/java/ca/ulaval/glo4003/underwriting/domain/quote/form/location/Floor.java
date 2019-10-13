package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Floor extends ValueObject {
  private int value;

  public Floor(String value, FloorFormatter floorFormatter) throws InvalidArgumentException {
    this.value = floorFormatter.format(value);
  }

  public int getValue() {
    return value;
  }
}
