package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.temporal.Date;

import java.util.Optional;

public class QuoteRequest {
  private IdentityRequest personalInformation;
  private IdentityRequest additionalInsured;
  private LocationRequest location;
  private Date effectiveDate;
  private BuildingRequest building;
  private PersonalPropertyRequest personalProperty;
  private CivilLiabilityRequest civilLiability;

  private QuoteRequest() {}

  public QuoteRequest(
      IdentityRequest personalInformation,
      IdentityRequest additionalInsured,
      LocationRequest location,
      Date effectiveDate,
      BuildingRequest building,
      PersonalPropertyRequest personalProperty,
      CivilLiabilityRequest civilLiability) {
    this.personalInformation = personalInformation;
    this.additionalInsured = additionalInsured;
    this.location = location;
    this.effectiveDate = effectiveDate;
    this.building = building;
    this.personalProperty = personalProperty;
    this.civilLiability = civilLiability;
  }

  public IdentityRequest getPersonalInformation() {
    return personalInformation;
  }

  public Optional<IdentityRequest> getAdditionalInsured() {
    return Optional.ofNullable(additionalInsured);
  }

  public LocationRequest getLocation() {
    return location;
  }

  public Date getEffectiveDate() {
    return effectiveDate;
  }

  public BuildingRequest getBuilding() {
    return building;
  }

  public PersonalPropertyRequest getPersonalProperty() {
    return personalProperty;
  }

  public Optional<CivilLiabilityRequest> getCivilLiability() {
    return Optional.ofNullable(civilLiability);
  }
}
