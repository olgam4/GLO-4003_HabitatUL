package ca.ulaval.glo4003.generator.quote.form.location;

import ca.ulaval.glo4003.gateway.presentation.quote.request.LocationView;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.PostalCode;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location.CanadianPostalCodeFormatter;
import com.github.javafaker.Faker;

public class LocationGenerator {
  private static final int MIN_STREET_NUMBER = 1;
  private static final int MAX_STREET_NUMBER = 1000;

  public static LocationView createLocationView() {
    return new LocationView(
        createPostalCode(), getRandomStreetNumber(), createApartmentNumber(), createFloor());
  }

  public static Location createLocation() {
    return new Location(
        createPostalCode(), getRandomStreetNumber(), createApartmentNumber(), createFloor());
  }

  private static PostalCode createPostalCode() {
    return new PostalCode("G1V4L3", new CanadianPostalCodeFormatter());
  }

  private static int getRandomStreetNumber() {
    return Faker.instance().number().numberBetween(MIN_STREET_NUMBER, MAX_STREET_NUMBER);
  }

  private static String createApartmentNumber() {
    return Faker.instance().number().digit();
  }

  private static Floor createFloor() {
    return new Floor("1ST");
  }
}
