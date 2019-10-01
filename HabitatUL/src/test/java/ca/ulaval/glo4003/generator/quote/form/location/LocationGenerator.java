package ca.ulaval.glo4003.generator.quote.form.location;

import ca.ulaval.glo4003.gateway.presentation.quote.request.LocationView;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Floor;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.PostalCode;
import ca.ulaval.glo4003.underwriting.infrastructure.quote.form.location.CanadianPostalCodeFormatter;

public class LocationGenerator {
  public static LocationView createLocationView() {
    // TODO: remove duplicate
    // TODO: add createIdentityDto
    // TODO: use assemblers to pass from entity to dto to view such as QuoteForm
    return new LocationView(createPostalCode(), 100, 2, createFloor());
  }

  public static Location createLocation() {
    return new Location(createPostalCode(), 100, 2, createFloor());
  }

  private static PostalCode createPostalCode() {
    return new PostalCode("G1V4L3", new CanadianPostalCodeFormatter());
  }

  private static Floor createFloor() {
    return new Floor("1ST");
  }
}
