package ca.ulaval.glo4003.underwriting.application.quote.dto;

import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;

public class QuoteFormDto {
  Identity identity;
  Location location;
  Date effectiveDate;

  public QuoteFormDto(Identity identity, Location location, Date effectiveDate) {
    this.identity = identity;
    this.location = location;
    this.effectiveDate = effectiveDate;
  }

  public Identity getIdentity() {
    return identity;
  }

  public Location getLocation() {
    return location;
  }

  public Date getEffectiveDate() {
    return effectiveDate;
  }
}
