package ca.ulaval.glo4003.underwriting.matcher;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.*;
import ca.ulaval.glo4003.gateway.presentation.underwriting.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import org.hamcrest.Matcher;

import java.util.Optional;

import static org.hamcrest.Matchers.*;

public class QuoteMatcher {
  private QuoteMatcher() {}

  public static Matcher<Quote> matchesQuote(final Quote quote) {
    return allOf(
        hasProperty("quoteId", equalTo(quote.getQuoteId())),
        hasProperty("status", equalTo(quote.getStatus())),
        hasProperty("quoteForm", equalTo(quote.getQuoteForm())),
        hasProperty("expirationDate", equalTo(quote.getExpirationDate())),
        hasProperty("effectivePeriod", equalTo(quote.getEffectivePeriod())),
        hasProperty("coverageDetails", equalTo(quote.getCoverageDetails())),
        hasProperty("premiumDetails", equalTo(quote.getPremiumDetails())));
  }

  public static Matcher<QuoteDto> matchesQuoteDto(final Quote quote) {
    return allOf(
        hasProperty("quoteId", equalTo(quote.getQuoteId())),
        hasProperty("expirationDate", equalTo(quote.getExpirationDate())),
        hasProperty("effectivePeriod", equalTo(quote.getEffectivePeriod())),
        hasProperty("coverageDetails", equalTo(quote.getCoverageDetails())),
        hasProperty("premiumDetails", equalTo(quote.getPremiumDetails())));
  }

  public static Matcher<QuoteForm> matchesQuoteForm(final RequestQuoteDto requestQuoteDto) {
    return allOf(
        hasProperty("personalInformation", equalTo(requestQuoteDto.getPersonalInformation())),
        hasProperty("additionalInsured", equalTo(requestQuoteDto.getAdditionalInsured())),
        hasProperty("location", equalTo(requestQuoteDto.getLocation())),
        hasProperty("effectiveDate", equalTo(requestQuoteDto.getEffectiveDate())),
        hasProperty("building", equalTo(requestQuoteDto.getBuilding())),
        hasProperty("personalProperty", equalTo(requestQuoteDto.getPersonalProperty())),
        hasProperty("civilLiability", equalTo(requestQuoteDto.getCivilLiability())));
  }

  public static Matcher<Identity> matchesIdentity(final IdentityRequest identityRequest) {
    return allOf(
        hasProperty("firstName", equalTo(identityRequest.getFirstName())),
        hasProperty("lastName", equalTo(identityRequest.getLastName())),
        hasProperty("birthDate", equalTo(identityRequest.getBirthDate())),
        hasProperty("gender", equalTo(identityRequest.getGender())),
        hasProperty(
            "universityProfile", matchesUniversityProfile(identityRequest.getUniversityProfile())));
  }

  public static Matcher<UniversityProfile> matchesUniversityProfile(
      final Optional<UniversityProfileRequest> universityProfileRequest) {
    UniversityProfileRequest universityProfile = universityProfileRequest.get();
    return allOf(
        hasProperty("idul", equalTo(universityProfile.getIdul())),
        hasProperty("identificationNumber", equalTo(universityProfile.getNi())),
        hasProperty("program", matchesUniversityProgram(universityProfile.getProgram())));
  }

  public static Matcher<UniversityProgram> matchesUniversityProgram(
      final UniversityProgramRequest universityProgramRequest) {
    return allOf(
        hasProperty("cycle", equalTo(universityProgramRequest.getCycle())),
        hasProperty("degree", equalTo(universityProgramRequest.getDegree())),
        hasProperty("major", equalTo(universityProgramRequest.getMajor())));
  }

  public static Matcher<Location> matchesLocation(final LocationRequest locationRequest) {
    return allOf(
        hasProperty("zipCode", equalTo(locationRequest.getZipCode())),
        hasProperty("streetNumber", equalTo(locationRequest.getStreetNumber())),
        hasProperty("apartmentNumber", equalTo(locationRequest.getApartmentNumber())),
        hasProperty("floor", equalTo(locationRequest.getFloor())));
  }

  public static Matcher<Building> matchesBuilding(final BuildingRequest buildingRequest) {
    return allOf(
        hasProperty("numberOfUnits", equalTo(buildingRequest.getNumberOfUnits())),
        hasProperty("preventionSystems", equalTo(buildingRequest.getPreventionSystems().get())),
        hasProperty("commercialUse", equalTo(buildingRequest.getCommercialUse().get())));
  }

  public static Matcher<Animals> matchesAnimal(final Animals animals) {
    return hasProperty("collection", equalTo(animals.getCollection()));
  }

  public static Matcher<PersonalProperty> matchesPersonalProperty(
      final PersonalPropertyRequest personalPropertyRequest) {
    return allOf(
        hasProperty("coverageAmount", equalTo(personalPropertyRequest.getCoverageAmount())),
        hasProperty("animals", matchesAnimal(personalPropertyRequest.getAnimals().get())));
  }

  public static Matcher<CivilLiability> matchesCivilLiability(
      final Optional<CivilLiabilityRequest> civilLiabilityRequest) {
    return allOf(hasProperty("limit", equalTo(civilLiabilityRequest.get().getLimit())));
  }

  public static Matcher<RequestQuoteDto> matchesRequestQuoteDto(final QuoteRequest quoteRequest) {
    return allOf(
        hasProperty("personalInformation", matchesIdentity(quoteRequest.getPersonalInformation())),
        hasProperty(
            "additionalInsured", matchesIdentity(quoteRequest.getAdditionalInsured().get())),
        hasProperty("location", matchesLocation(quoteRequest.getLocation())),
        hasProperty("effectiveDate", equalTo(quoteRequest.getEffectiveDate())),
        hasProperty("building", matchesBuilding(quoteRequest.getBuilding())),
        hasProperty(
            "personalProperty", matchesPersonalProperty(quoteRequest.getPersonalProperty())),
        hasProperty("civilLiability", matchesCivilLiability(quoteRequest.getCivilLiability())));
  }
}
