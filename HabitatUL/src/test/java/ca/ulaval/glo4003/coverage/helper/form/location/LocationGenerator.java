package ca.ulaval.glo4003.coverage.helper.form.location;

import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.LocationRequest;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.shared.helper.AddressGenerator.createFloor;
import static ca.ulaval.glo4003.shared.helper.AddressGenerator.createZipCode;

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
