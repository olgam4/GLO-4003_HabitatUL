package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCode;

public class LocationView {
  private ZipCode zipCode;
  private int streetNumber;
  private String apartmentNumber;
  private Floor floor;

  public LocationView(ZipCode zipCode, int streetNumber, String apartmentNumber, Floor floor) {
    this.zipCode = zipCode;
    this.streetNumber = streetNumber;
    this.apartmentNumber = apartmentNumber;
    this.floor = floor;
  }

  // IMPORTANT - KEEP FOR JACKSON SERIALIZATION
  private LocationView() {}

  public ZipCode getZipCode() {
    return zipCode;
  }

  public int getStreetNumber() {
    return streetNumber;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public Floor getFloor() {
    return floor;
  }
}
