package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.gateway.presentation.quote.request.*;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;
import org.hamcrest.Matcher;

import java.util.Optional;

import static org.hamcrest.Matchers.*;

public class QuoteMatcher {
  public static Matcher<Quote> matchesQuote(final Quote quote) {
    return allOf(
        hasProperty("quoteId", equalTo(quote.getQuoteId())),
        hasProperty("price", equalTo(quote.getPrice())),
        hasProperty("purchased", equalTo(quote.isPurchased())),
        hasProperty("effectivePeriod", equalTo(quote.getEffectivePeriod())),
        hasProperty("expirationDate", equalTo(quote.getExpirationDate())));
  }

  public static Matcher<QuoteDto> matchesQuoteDto(final Quote quote) {
    return allOf(
        hasProperty("quoteId", equalTo(quote.getQuoteId())),
        hasProperty("price", equalTo(quote.getPrice())),
        hasProperty("effectivePeriod", equalTo(quote.getEffectivePeriod())),
        hasProperty("expirationDate", equalTo(quote.getExpirationDate())));
  }

  public static Matcher<QuoteForm> matchesQuoteForm(final QuoteFormDto quoteFormDto) {
    return allOf(
        hasProperty("personalInformation", equalTo(quoteFormDto.getPersonalInformation())),
        hasProperty("additionalInsured", equalTo(quoteFormDto.getAdditionalInsured())),
        hasProperty("location", equalTo(quoteFormDto.getLocation())),
        hasProperty("effectiveDate", equalTo(quoteFormDto.getEffectiveDate())),
        hasProperty("building", equalTo(quoteFormDto.getBuilding())),
        hasProperty("personalProperty", equalTo(quoteFormDto.getPersonalProperty())),
        hasProperty("civilLiability", equalTo(quoteFormDto.getCivilLiability())));
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
        hasProperty("degree", equalTo(universityProfile.getDegree())),
        hasProperty("program", equalTo(universityProfile.getProgram())));
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
        hasProperty("preventionSystems", equalTo(buildingRequest.getPreventionSystems())),
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

  public static Matcher<QuoteFormDto> matchesQuoteFormDto(final QuoteRequest quoteRequest) {
    System.out.println(quoteRequest);
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
