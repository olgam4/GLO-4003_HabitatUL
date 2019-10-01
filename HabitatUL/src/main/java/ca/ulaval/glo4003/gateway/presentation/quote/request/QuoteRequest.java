package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.Date;

public class QuoteRequest {
  private IdentityView identity;
  private LocationView location;
  private Date effectiveDate;

  public QuoteRequest(IdentityView identity, LocationView location, Date effectiveDate) {
    this.identity = identity;
    this.location = location;
    this.effectiveDate = effectiveDate;
  }

  // IMPORTANT - KEEP FOR JACKSON SERIALIZATION
  private QuoteRequest() {}

  public IdentityView getIdentity() {
    return identity;
  }

  public LocationView getLocation() {
    return location;
  }

  public Date getEffectiveDate() {
    return effectiveDate;
  }
}
