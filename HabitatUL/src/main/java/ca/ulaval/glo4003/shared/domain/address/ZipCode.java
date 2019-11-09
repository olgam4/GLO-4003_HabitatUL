package ca.ulaval.glo4003.shared.domain.address;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;

public class ZipCode extends ValueObject {
  private final String value;

  public ZipCode(String value, ZipCodeFormatter zipCodeFormatter) throws InvalidArgumentException {
    this.value = zipCodeFormatter.format(value);
  }

  public String getValue() {
    return value;
  }
}
