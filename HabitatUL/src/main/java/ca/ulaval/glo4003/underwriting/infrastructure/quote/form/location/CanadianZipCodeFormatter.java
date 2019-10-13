package ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCodeFormatter;

public class CanadianZipCodeFormatter implements ZipCodeFormatter {
  private static final String CANADIAN_ZIP_CODE_REGEX = "[A-Z]\\d[A-Z]\\d[A-Z]\\d";

  @Override
  public String format(String zipCodeValue) throws InvalidArgumentException {
    String upperCaseZipCode = zipCodeValue.toUpperCase().replaceAll("\\s+", "");

    if (upperCaseZipCode.matches(CANADIAN_ZIP_CODE_REGEX)) return upperCaseZipCode;
    else throw new InvalidArgumentException();
  }
}
