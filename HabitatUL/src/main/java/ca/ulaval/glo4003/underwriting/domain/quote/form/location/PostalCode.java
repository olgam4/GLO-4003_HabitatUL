package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class PostalCode extends ValueObject {
  private String value;

  public PostalCode(String value, PostalCodeFormatter postalCodeFormatter) {
    this.value = postalCodeFormatter.format(value);
  }

  public String getValue() {
    return value;
  }
}
