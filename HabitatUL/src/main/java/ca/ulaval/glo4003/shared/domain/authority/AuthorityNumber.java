package ca.ulaval.glo4003.shared.domain.authority;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

public class AuthorityNumber extends ValueObject {
  public static final AuthorityNumber UNFILLED_AUTHORITY_NUMBER = new AuthorityNumber(null, null);

  private final String value;
  private final Date date;

  public AuthorityNumber(
      String value,
      AuthorityNumberFormatter authorityNumberFormatter,
      AuthorityNumberParser authorityNumberParser)
      throws InvalidArgumentException {
    this(authorityNumberFormatter.format(value), authorityNumberParser.parseDate(value));
  }

  private AuthorityNumber(String value, Date date) {
    this.value = value;
    this.date = date;
  }

  public String getValue() {
    return value;
  }

  public String toRepresentation() {
    return value;
  }

  public boolean isFilled() {
    return !equals(UNFILLED_AUTHORITY_NUMBER);
  }

  public boolean declaredOn(Date otherDate) {
    return date.equals(otherDate);
  }
}
