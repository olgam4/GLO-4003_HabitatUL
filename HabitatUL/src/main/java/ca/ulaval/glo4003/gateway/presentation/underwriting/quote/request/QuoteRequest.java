package ca.ulaval.glo4003.gateway.presentation.underwriting.quote.request;

import ca.ulaval.glo4003.gateway.presentation.coverage.request.*;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class QuoteRequest {
  @NotNull @Valid private IdentityRequest personalInformation;
  @Valid private IdentityRequest additionalInsured;
  @NotNull @Valid private LocationRequest location;
  @NotNull private Date effectiveDate;
  @NotNull @Valid private BuildingRequest building;
  @NotNull @Valid private PersonalPropertyRequest personalProperty;
  @Valid private CivilLiabilityRequest civilLiability;

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
