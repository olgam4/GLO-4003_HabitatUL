package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.BaseException;

public class InvalidFloorException extends BaseException {
  private String invalidFloorValue;

  public InvalidFloorException(String invalidFloorValue) {
    this.invalidFloorValue = invalidFloorValue;
  }

  public String getInvalidFloor() {
    return invalidFloorValue;
  }
}
