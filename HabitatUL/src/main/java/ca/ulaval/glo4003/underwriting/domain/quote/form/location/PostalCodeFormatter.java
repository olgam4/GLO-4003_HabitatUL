package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;

public interface PostalCodeFormatter {
  String format(String postalCodeValue) throws InvalidArgumentException;
}
