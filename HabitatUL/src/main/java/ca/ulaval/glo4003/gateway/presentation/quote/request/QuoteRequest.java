package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.Date;

public class QuoteRequest {
  private IdentityView identity;
  private LocationView location;
  private Date effectiveDate;
  private BuildingView building;

  public QuoteRequest(
      IdentityView identity, LocationView location, Date effectiveDate, BuildingView building) {
    this.identity = identity;
    this.location = location;
    this.effectiveDate = effectiveDate;
    this.building = building;
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

  public BuildingView getBuilding() {
    return building;
  }
}
