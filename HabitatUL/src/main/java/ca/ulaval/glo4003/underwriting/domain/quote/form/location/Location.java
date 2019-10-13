package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

public class Location {
  private ZipCode zipCode;
  private int streetNumber;
  private String apartmentNumber;
  private Floor floor;

  public Location(ZipCode zipCode, Integer streetNumber, String apartmentNumber, Floor floor) {
    this.zipCode = zipCode;
    this.streetNumber = streetNumber;
    this.apartmentNumber = apartmentNumber;
    this.floor = floor;
  }

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
