package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.Date;

public class QuoteRequest {
  private IdentityView identity;
  private LocationView location;
  private Date effectiveDate;
  private BuildingView building;
  private PersonalPropertyView personalProperty;

  public QuoteRequest(
      IdentityView identity,
      LocationView location,
      Date effectiveDate,
      BuildingView building,
      PersonalPropertyView personalProperty) {
    this.identity = identity;
    this.location = location;
    this.effectiveDate = effectiveDate;
    this.building = building;
    this.personalProperty = personalProperty;
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

  public PersonalPropertyView getPersonalProperty() {
    return personalProperty;
  }
}
