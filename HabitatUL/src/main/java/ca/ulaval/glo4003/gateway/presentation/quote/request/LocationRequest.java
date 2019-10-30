package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCode;

import javax.validation.constraints.NotNull;

public class LocationRequest {
  @NotNull private ZipCode zipCode;
  @NotNull private Integer streetNumber;
  @NotNull private String apartmentNumber;
  @NotNull private Floor floor;

  private LocationRequest() {}

  public LocationRequest(
      ZipCode zipCode, Integer streetNumber, String apartmentNumber, Floor floor) {
    this.zipCode = zipCode;
    this.streetNumber = streetNumber;
    this.apartmentNumber = apartmentNumber;
    this.floor = floor;
  }

  public ZipCode getZipCode() {
    return zipCode;
  }

  public Integer getStreetNumber() {
    return streetNumber;
  }

  public String getApartmentNumber() {
    return apartmentNumber;
  }

  public Floor getFloor() {
    return floor;
  }
}
