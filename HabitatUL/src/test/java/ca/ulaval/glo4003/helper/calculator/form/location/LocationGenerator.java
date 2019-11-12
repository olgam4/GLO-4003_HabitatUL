package ca.ulaval.glo4003.helper.calculator.form.location;

import ca.ulaval.glo4003.calculator.domain.form.location.Location;
import ca.ulaval.glo4003.gateway.presentation.quote.request.LocationRequest;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.shared.AddressGenerator.createFloor;
import static ca.ulaval.glo4003.helper.shared.AddressGenerator.createZipCode;

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
