package ca.ulaval.glo4003.shared.domain.authority;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class SpvqAuthorityNumberFormatter
    implements AuthorityNumberFormatter, AuthorityNumberParser {
  private static final int CURRENT_MILLENNIUM = 2000;

  @Override
  public String format(String value) throws InvalidArgumentException {
    String authorityNumber = value.toUpperCase();
    validateLength(authorityNumber);
    validatePrefix(authorityNumber);
    validateDate(authorityNumber);
    validateCounter(authorityNumber);
    return authorityNumber;
  }

  private void validateLength(String value) throws InvalidArgumentException {
    if (value.length() != 12) throw new InvalidArgumentException();
  }

  private void validatePrefix(String value) throws InvalidArgumentException {
    String prefix = value.substring(0, 3);
    if (!prefix.equals("QUE")) throw new InvalidArgumentException();
  }

  private void validateDate(String value) throws InvalidArgumentException {
    try {
      parseDate(value);
    } catch (Exception e) {
      throw new InvalidArgumentException();
    }
  }

  @Override
  public Date parseDate(String value) throws InvalidArgumentException {
    String date = value.substring(3, 9);
    int year = parseInt(date.substring(0, 2)) + CURRENT_MILLENNIUM;
    int month = parseInt(date.substring(2, 4));
    int day = parseInt(date.substring(4, 6));
    try {
      return Date.from(LocalDate.of(year, month, day));
    } catch (Exception e) {
      throw new InvalidArgumentException();
    }
  }

  private void validateCounter(String value) throws InvalidArgumentException {
    String counter = value.substring(9, 12);
    try {
      parseInt(counter);
    } catch (Exception e) {
      throw new InvalidArgumentException();
    }
  }
}
