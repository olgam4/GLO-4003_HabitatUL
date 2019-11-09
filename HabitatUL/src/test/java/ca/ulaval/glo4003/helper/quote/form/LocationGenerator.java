package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.LocationRequest;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.AddressGenerator.createFloor;
import static ca.ulaval.glo4003.helper.AddressGenerator.createZipCode;

public class LocationGenerator {
  private LocationGenerator() {}

  public static LocationRequest createLocationRequest() {
    return new LocationRequest(
        createZipCode(), createStreetNumber(), createApartmentNumber(), createFloor());
  }

  public static Location createLocation() {
    return new Location(
        createZipCode(), createStreetNumber(), createApartmentNumber(), createFloor());
  }

  public static String createStreetNumber() {
    return Faker.instance().address().streetName();
  }

  public static String createApartmentNumber() {
    return Faker.instance().number().digit();
  }
}
