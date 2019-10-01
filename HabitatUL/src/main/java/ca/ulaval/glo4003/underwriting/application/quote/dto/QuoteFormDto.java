package ca.ulaval.glo4003.underwriting.application.quote.dto;

import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;

public class QuoteFormDto {
  Identity identity;
  Location location;

  public QuoteFormDto(Identity identity, Location location) {
    this.identity = identity;
    this.location = location;
  }

  public Identity getIdentity() {
    return identity;
  }

  public Location getLocation() {
    return location;
  }
}
