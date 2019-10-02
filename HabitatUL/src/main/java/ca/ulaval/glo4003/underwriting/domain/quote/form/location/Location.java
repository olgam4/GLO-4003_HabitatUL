package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

public class Location {
  private PostalCode postalCode;
  private int streetNumber;
  private String apartmentNumber;
  private Floor floor;

  public Location(
      PostalCode postalCode, Integer streetNumber, String apartmentNumber, Floor floor) {
    this.postalCode = postalCode;
    this.streetNumber = streetNumber;
    this.apartmentNumber = apartmentNumber;
    this.floor = floor;
  }

  public PostalCode getPostalCode() {
    return postalCode;
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
