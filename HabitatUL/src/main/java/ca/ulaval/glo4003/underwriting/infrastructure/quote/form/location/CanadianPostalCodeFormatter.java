package ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.PostalCodeFormatter;

public class CanadianPostalCodeFormatter implements PostalCodeFormatter {
  private static final String CANADIAN_POSTAL_CODE_REGEX = "[A-Z]\\d[A-Z]\\d[A-Z]\\d";

  @Override
  public String format(String value) throws InvalidArgumentException {
    String upperCasePostalCode = value.toUpperCase().replaceAll("\\s+", "");

    if (upperCasePostalCode.matches(CANADIAN_POSTAL_CODE_REGEX)) return upperCasePostalCode;
    else throw new InvalidArgumentException();
  }
}
