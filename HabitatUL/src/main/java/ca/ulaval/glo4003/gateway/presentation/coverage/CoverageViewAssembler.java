package ca.ulaval.glo4003.gateway.presentation.coverage;

import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.*;

import java.util.Optional;

import static ca.ulaval.glo4003.coverage.domain.form.building.Building.NO_COMMERCIAL_USE;
import static ca.ulaval.glo4003.coverage.domain.form.building.PreventionSystems.UNFILLED_PREVENTION_SYSTEMS;
import static ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability.UNFILLED_CIVIL_LIABILITY;
import static ca.ulaval.glo4003.coverage.domain.form.identity.Identity.UNFILLED_IDENTITY;
import static ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile.UNFILLED_UNIVERSITY_PROFILE;
import static ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals.UNFILLED_ANIMALS;
import static ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle.UNFILLED_BICYCLE;

public class CoverageViewAssembler {
  public Identity fromIdentityRequest(Optional<IdentityRequest> identityRequest) {
    return identityRequest.map(this::fromIdentityRequest).orElse(UNFILLED_IDENTITY);
  }

  public Identity fromIdentityRequest(IdentityRequest identityRequest) {
    return new Identity(
        identityRequest.getFirstName(),
        identityRequest.getLastName(),
        identityRequest.getBirthDate(),
        identityRequest.getGender(),
        fromUniversityProfileRequest(identityRequest.getUniversityProfile()));
  }

  private UniversityProfile fromUniversityProfileRequest(
      Optional<UniversityProfileRequest> universityProfileRequest) {
    return universityProfileRequest
        .map(this::fromUniversityProfileRequest)
        .orElse(UNFILLED_UNIVERSITY_PROFILE);
  }

  private UniversityProfile fromUniversityProfileRequest(
      UniversityProfileRequest universityProfileRequest) {
    return new UniversityProfile(
        universityProfileRequest.getIdul(),
        universityProfileRequest.getNi(),
        from(universityProfileRequest.getProgram()));
  }

  private UniversityProgram from(UniversityProgramRequest universityProgramRequest) {
    return new UniversityProgram(
        universityProgramRequest.getCycle(),
        universityProgramRequest.getDegree(),
        universityProgramRequest.getMajor());
  }

  public Location fromLocationRequest(LocationRequest locationRequest) {
    return new Location(
        locationRequest.getZipCode(),
        locationRequest.getStreetNumber(),
        locationRequest.getApartmentNumber(),
        locationRequest.getFloor());
  }

  public Building fromBuildingRequest(BuildingRequest buildingRequest) {
    return new Building(
        buildingRequest.getNumberOfUnits(),
        buildingRequest.getPreventionSystems().orElse(UNFILLED_PREVENTION_SYSTEMS),
        buildingRequest.getCommercialUse().orElse(NO_COMMERCIAL_USE));
  }

  public PersonalProperty fromPersonalPropertyRequest(
      PersonalPropertyRequest personalPropertyRequest) {
    return new PersonalProperty(
        personalPropertyRequest.getCoverageAmount(),
        personalPropertyRequest.getAnimals().orElse(UNFILLED_ANIMALS),
        fromBicycleRequest(personalPropertyRequest.getBicycle()));
  }

  public Bicycle fromBicycleRequest(Optional<BicycleRequest> bicycleRequest) {
    return bicycleRequest.map(this::fromBicycleRequest).orElse(UNFILLED_BICYCLE);
  }

  public Bicycle fromBicycleRequest(BicycleRequest bicycleRequest) {
    return new Bicycle(
        bicycleRequest.getPrice(),
        bicycleRequest.getBrand(),
        bicycleRequest.getModel(),
        bicycleRequest.getYear());
  }

  public CivilLiability fromCivilLiabilityRequest(
      Optional<CivilLiabilityRequest> civilLiabilityRequest) {
    return civilLiabilityRequest
        .map(this::fromCivilLiabilityRequest)
        .orElse(UNFILLED_CIVIL_LIABILITY);
  }

  private CivilLiability fromCivilLiabilityRequest(CivilLiabilityRequest civilLiabilityRequest) {
    return new CivilLiability(civilLiabilityRequest.getLimit());
  }
}
