package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.temporal.Date;

import java.util.Optional;

public class QuoteRequest {
  private IdentityRequest identity;
  private LocationRequest location;
  private Date effectiveDate;
  private BuildingRequest building;
  private PersonalPropertyRequest personalProperty;
  private CivilLiabilityRequest civilLiability;
  private StudentInformationRequest studentInformation;

  private QuoteRequest() {}

  public QuoteRequest(
      IdentityRequest identity,
      LocationRequest location,
      Date effectiveDate,
      BuildingRequest building,
      PersonalPropertyRequest personalProperty,
      CivilLiabilityRequest civilLiability,
      StudentInformationRequest studentInformation) {
    this.identity = identity;
    this.location = location;
    this.effectiveDate = effectiveDate;
    this.building = building;
    this.personalProperty = personalProperty;
    this.civilLiability = civilLiability;
    this.studentInformation = studentInformation;
  }

  public IdentityRequest getIdentity() {
    return identity;
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

  public StudentInformationRequest getStudentInformation() {
    return studentInformation;
  }
}
