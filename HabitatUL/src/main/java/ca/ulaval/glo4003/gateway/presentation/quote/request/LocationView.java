package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.PostalCode;

public class LocationView {
  private PostalCode postalCode;
  private int streetNumber;
  private int apartmentNumber;
  private Floor floor;

  public LocationView(PostalCode postalCode, int streetNumber, int apartmentNumber, Floor floor) {
    this.postalCode = postalCode;
    this.streetNumber = streetNumber;
    this.apartmentNumber = apartmentNumber;
    this.floor = floor;
  }

  // IMPORTANT - KEEP FOR JACKSON SERIALIZATION
  private LocationView() {}

  public PostalCode getPostalCode() {
    return postalCode;
  }

  public int getStreetNumber() {
    return streetNumber;
  }

  public int getApartmentNumber() {
    return apartmentNumber;
  }

  public Floor getFloor() {
    return floor;
  }
}
