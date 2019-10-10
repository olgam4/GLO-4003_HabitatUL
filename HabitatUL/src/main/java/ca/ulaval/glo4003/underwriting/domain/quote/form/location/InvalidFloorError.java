package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.BaseError;

public class InvalidFloorError extends BaseError {
  private String invalidFloorValue;

  public InvalidFloorError(String invalidFloorValue) {
    this.invalidFloorValue = invalidFloorValue;
  }

  public String getInvalidFloor() {
    return invalidFloorValue;
  }
}
