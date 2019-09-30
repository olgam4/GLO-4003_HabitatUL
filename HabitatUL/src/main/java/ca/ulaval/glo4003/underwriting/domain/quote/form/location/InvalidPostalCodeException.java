package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.BaseException;

public class InvalidPostalCodeException extends BaseException {
  private String invalidPostalCode;

  public InvalidPostalCodeException(String invalidPostalCode) {
    this.invalidPostalCode = invalidPostalCode;
  }

  public String getInvalidPostalCode() {
    return invalidPostalCode;
  }
}
