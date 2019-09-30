package ca.ulaval.glo4003.underwriting.domain.quote.form.location;

public class Location {
  private PostalCode postalCode;
  private Integer streetNumber;
  private Integer apartmentNumber;
  private Floor floor;

  public Location(
      PostalCode postalCode, Integer streetNumber, Integer apartmentNumber, Floor floor) {
    this.postalCode = postalCode;
    this.streetNumber = streetNumber;
    this.apartmentNumber = apartmentNumber;
    this.floor = floor;
  }
}
