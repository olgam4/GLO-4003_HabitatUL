package ca.ulaval.glo4003.coverage.domain.form.location;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.address.Floor;
import ca.ulaval.glo4003.shared.domain.address.ZipCode;

public class Location extends ValueObject {
  private final ZipCode zipCode;
  private final String streetNumber;
  private final String apartmentNumber;
  private final Floor floor;

  public Location(ZipCode zipCode, String streetNumber, String apartmentNumber, Floor floor) {
    this.zipCode = zipCode;
    this.streetNumber = streetNumber;
    this.apartmentNumber = apartmentNumber;
    this.floor = floor;
  }

  public ZipCode getZipCode() {
    return zipCode;
  }

  public String getStreetNumber() {
    return streetNumber;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public Floor getFloor() {
    return floor;
  }
}
