package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import ca.ulaval.glo4003.shared.domain.address.Floor;
import ca.ulaval.glo4003.shared.domain.address.ZipCode;

import javax.validation.constraints.NotNull;

public class LocationRequest {
  @NotNull private ZipCode zipCode;
  @NotNull private String streetNumber;
  @NotNull private String apartmentNumber;
  @NotNull private Floor floor;

  private LocationRequest() {}

  public LocationRequest(
      ZipCode zipCode, String streetNumber, String apartmentNumber, Floor floor) {
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
