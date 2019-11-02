package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Location extends ValueObject {
  private ZipCode zipCode;
  private String streetNumber;
  private String apartmentNumber;
  private Floor floor;

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
