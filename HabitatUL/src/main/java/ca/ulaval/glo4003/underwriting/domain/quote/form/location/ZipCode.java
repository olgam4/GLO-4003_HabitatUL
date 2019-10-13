package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.ValueObject;

public class ZipCode extends ValueObject {
  private String value;

  public ZipCode(String value, ZipCodeFormatter zipCodeFormatter) throws InvalidArgumentException {
    this.value = zipCodeFormatter.format(value);
  }

  public String getValue() {
    return value;
  }
}
