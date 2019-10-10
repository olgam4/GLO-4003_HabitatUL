package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.BaseError;

public class InvalidPostalCodeError extends BaseError {
  private String invalidPostalCode;

  public InvalidPostalCodeError(String invalidPostalCode) {
    this.invalidPostalCode = invalidPostalCode;
  }

  public String getInvalidPostalCode() {
    return invalidPostalCode;
  }
}
