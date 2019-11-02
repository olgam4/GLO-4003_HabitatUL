package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.request.*;
import ca.ulaval.glo4003.gateway.presentation.quote.response.QuoteCoverageOverviewResponse;
import ca.ulaval.glo4003.gateway.presentation.quote.response.QuoteResponse;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteCoverageOverviewDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;

import java.util.Optional;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building.NO_COMMERCIAL_USE;
import static ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystems.UNFILLED_PREVENTION_SYSTEMS;
import static ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability.UNFILLED_CIVIL_LIABILITY;
import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity.UNFILLED_IDENTITY;
import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile.UNFILLED_UNIVERSITY_PROFILE;
import static ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals.UNFILLED_ANIMALS;

public class QuoteViewAssembler {
  public QuoteFormDto from(QuoteRequest quoteRequest) {
    return new QuoteFormDto(
        fromIdentityRequest(quoteRequest.getPersonalInformation()),
        fromIdentityRequest(quoteRequest.getAdditionalInsured()),
        fromLocationRequest(quoteRequest.getLocation()),
        quoteRequest.getEffectiveDate(),
        fromBuildingRequest(quoteRequest.getBuilding()),
        fromPersonalPropertyRequest(quoteRequest.getPersonalProperty()),
        fromCivilLiabilityRequest(quoteRequest.getCivilLiability()));
  }

  private Identity fromIdentityRequest(Optional<IdentityRequest> identityRequest) {
    return identityRequest.map(this::fromIdentityRequest).orElse(UNFILLED_IDENTITY);
  }

  private Identity fromIdentityRequest(IdentityRequest identityRequest) {
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
        universityProfileRequest.getCycle(),
        universityProfileRequest.getDegree(),
        universityProfileRequest.getProgram());
  }

  private Location fromLocationRequest(LocationRequest locationRequest) {
    return new Location(
        locationRequest.getZipCode(),
        locationRequest.getStreetNumber(),
        locationRequest.getApartmentNumber(),
        locationRequest.getFloor());
  }

  private Building fromBuildingRequest(BuildingRequest buildingRequest) {
    return new Building(
        buildingRequest.getNumberOfUnits(),
        buildingRequest.getPreventionSystems().orElse(UNFILLED_PREVENTION_SYSTEMS),
        buildingRequest.getCommercialUse().orElse(NO_COMMERCIAL_USE));
  }

  private PersonalProperty fromPersonalPropertyRequest(
      PersonalPropertyRequest personalPropertyRequest) {
    Amount coverageAmount = personalPropertyRequest.getCoverageAmount();
    Animals animals = personalPropertyRequest.getAnimals().orElse(UNFILLED_ANIMALS);
    return new PersonalProperty(coverageAmount, animals);
  }

  private CivilLiability fromCivilLiabilityRequest(
      Optional<CivilLiabilityRequest> civilLiabilityRequest) {
    return civilLiabilityRequest
        .map(this::fromCivilLiabilityRequest)
        .orElse(UNFILLED_CIVIL_LIABILITY);
  }

  private CivilLiability fromCivilLiabilityRequest(CivilLiabilityRequest civilLiabilityRequest) {
    return new CivilLiability(civilLiabilityRequest.getLimit());
  }

  public QuoteResponse from(QuoteDto quoteDto) {
    QuoteId quoteId = quoteDto.getQuoteId();
    Money price = quoteDto.getPrice();
    Period effectivePeriod = quoteDto.getEffectivePeriod();
    DateTime expirationDate = quoteDto.getExpirationDate();
    QuoteCoverageOverviewResponse coverage =
        fromQuoteCoverageOverviewDto(quoteDto.getCoverageOverview());
    return new QuoteResponse(quoteId, price, effectivePeriod, expirationDate, coverage);
  }

  private QuoteCoverageOverviewResponse fromQuoteCoverageOverviewDto(
      QuoteCoverageOverviewDto quoteCoverageOverviewDto) {
    return new QuoteCoverageOverviewResponse(
        quoteCoverageOverviewDto.getPersonalProperty(),
        quoteCoverageOverviewDto.getCivilLiability());
  }
}
