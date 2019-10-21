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
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;

import java.util.Optional;

public class QuoteViewAssembler {
  public QuoteFormDto from(QuoteRequest quoteRequest) {
    return new QuoteFormDto(
        fromIdentityRequest(quoteRequest.getIdentity()),
        fromLocationRequest(quoteRequest.getLocation()),
        quoteRequest.getEffectiveDate(),
        fromBuildingRequest(quoteRequest.getBuilding()),
        fromPersonalPropertyRequest(quoteRequest.getPersonalProperty()),
        fromCivilLiabilityRequest(quoteRequest.getCivilLiability()));
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
    String idul = universityProfileRequest.map(UniversityProfileRequest::getIdul).orElse(null);
    String identificationNumber =
        universityProfileRequest.map(UniversityProfileRequest::getNi).orElse(null);
    String program =
        universityProfileRequest.map(UniversityProfileRequest::getProgram).orElse(null);
    return new UniversityProfile(idul, identificationNumber, program);
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
        buildingRequest.getPreventionSystems(),
        buildingRequest.getCommercialUse());
  }

  private PersonalProperty fromPersonalPropertyRequest(
      PersonalPropertyRequest personalPropertyRequest) {
    Amount coverageAmount = personalPropertyRequest.getCoverageAmount();
    Animals animals = personalPropertyRequest.getAnimals();
    return new PersonalProperty(coverageAmount, animals);
  }

  private CivilLiability fromCivilLiabilityRequest(
      Optional<CivilLiabilityRequest> civilLiabilityRequest) {
    CivilLiabilityAmount coverageAmount =
        civilLiabilityRequest.map(CivilLiabilityRequest::getCoverageAmount).orElse(null);
    return new CivilLiability(coverageAmount);
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
