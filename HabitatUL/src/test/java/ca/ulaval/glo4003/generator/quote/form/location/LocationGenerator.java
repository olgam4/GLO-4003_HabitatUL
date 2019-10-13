package ca.ulaval.glo4003.generator.quote.form.location;

import ca.ulaval.glo4003.gateway.presentation.quote.request.LocationView;
import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCode;
import com.github.javafaker.Faker;

public class LocationGenerator {
  private static final int MIN_STREET_NUMBER = 1;
  private static final int MAX_STREET_NUMBER = 1000;

  public static LocationView createLocationView() {
    return new LocationView(
        createZipCode(), getRandomStreetNumber(), createApartmentNumber(), createFloor());
  }

  public static Location createLocation() {
    return new Location(
        createZipCode(), getRandomStreetNumber(), createApartmentNumber(), createFloor());
  }

  private static ZipCode createZipCode() {
    try {
      String zipCodeValue = Faker.instance().address().zipCode();
      return new ZipCode(zipCodeValue, zipCode -> zipCode);
    } catch (InvalidArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }

  private static int getRandomStreetNumber() {
    return Faker.instance().number().numberBetween(MIN_STREET_NUMBER, MAX_STREET_NUMBER);
  }

  private static String createApartmentNumber() {
    return Faker.instance().number().digit();
  }

  private static Floor createFloor() {
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
