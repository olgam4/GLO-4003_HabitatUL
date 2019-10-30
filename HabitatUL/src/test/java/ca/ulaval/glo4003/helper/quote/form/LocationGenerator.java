package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.LocationRequest;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCode;
import com.github.javafaker.Faker;

public class LocationGenerator {
  private static final int MIN_STREET_NUMBER = 1;
  private static final int MAX_STREET_NUMBER = 1000;

  public static LocationRequest createLocationRequest() {
    return new LocationRequest(
        createZipCode(), createStreetNumber(), createApartmentNumber(), createFloor());
  }

  public static Location createLocation() {
    return new Location(
        createZipCode(), createStreetNumber(), createApartmentNumber(), createFloor());
  }

  public static ZipCode createZipCode() {
    try {
      String zipCodeValue = Faker.instance().address().zipCode();
      return new ZipCode(zipCodeValue, zipCode -> zipCode);
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static String createStreetNumber() {
    return Faker.instance().address().streetName();
  }

  public static String createApartmentNumber() {
    return Faker.instance().number().digit();
  }

  public static Floor createFloor() {
    try {
      Faker faker = Faker.instance();
      String floorValue = faker.lordOfTheRings().location();
      return new Floor(floorValue, floor -> faker.number().randomDigit());
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }
}
