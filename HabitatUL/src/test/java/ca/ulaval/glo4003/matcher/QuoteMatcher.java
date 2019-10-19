package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.gateway.presentation.quote.request.*;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.underwriting.domain.quote.form.studentinformation.StudentInformation;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.hamcrest.core.AllOf.allOf;

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
        hasProperty("identity", equalTo(quoteFormDto.getIdentity())),
        hasProperty("location", equalTo(quoteFormDto.getLocation())),
        hasProperty("effectiveDate", equalTo(quoteFormDto.getEffectiveDate())),
        hasProperty("building", equalTo(quoteFormDto.getBuilding())),
        hasProperty("personalProperty", equalTo(quoteFormDto.getPersonalProperty())),
        hasProperty("studentInformation", equalTo(quoteFormDto.getStudentInformation())));
  }

  public static Matcher<Identity> matchesIdentity(final IdentityView identityView) {
    return allOf(
        hasProperty("firstName", equalTo(identityView.getFirstName())),
        hasProperty("lastName", equalTo(identityView.getLastName())),
        hasProperty("birthDate", equalTo(identityView.getBirthDate())),
        hasProperty("gender", equalTo(identityView.getGender())));
  }

  public static Matcher<Location> matchesLocation(final LocationView locationView) {
    return allOf(
        hasProperty("zipCode", equalTo(locationView.getZipCode())),
        hasProperty("streetNumber", equalTo(locationView.getStreetNumber())),
        hasProperty("apartmentNumber", equalTo(locationView.getApartmentNumber())),
        hasProperty("floor", equalTo(locationView.getFloor())));
  }

  public static Matcher<Building> matchesBuilding(final BuildingView buildingView) {
    return allOf(
        hasProperty("numberOfUnits", equalTo(buildingView.getNumberOfUnits())),
        hasProperty("preventionSystems", equalTo(buildingView.getPreventionSystems())),
        hasProperty("commercialUse", equalTo(buildingView.getCommercialUse())));
  }

  public static Matcher<Animals> matchesAnimal(final Animals animals) {
    return hasProperty("collection", equalTo(animals.getCollection()));
  }

  public static Matcher<PersonalProperty> matchesPersonalProperty(
      final PersonalPropertyView personalPropertyView) {
    return allOf(
        hasProperty("coverageAmount", equalTo(personalPropertyView.getCoverageAmount())),
        hasProperty("animals", matchesAnimal(personalPropertyView.getAnimals())));
  }

  public static Matcher<StudentInformation> matchesStudentInformation(
      final StudentInformationView studentInformationView) {
    return allOf(
        hasProperty("idul", equalTo(studentInformationView.getIdul())),
        hasProperty("identificationNumber", equalTo(studentInformationView.getNi())),
        hasProperty("program", equalTo(studentInformationView.getProgram())));
  }

  public static Matcher<QuoteFormDto> matchesQuoteFormDto(final QuoteRequest quoteRequest) {
    return allOf(
        hasProperty("identity", matchesIdentity(quoteRequest.getIdentity())),
        hasProperty("location", matchesLocation(quoteRequest.getLocation())),
        hasProperty("effectiveDate", equalTo(quoteRequest.getEffectiveDate())),
        hasProperty("building", matchesBuilding(quoteRequest.getBuilding())),
        hasProperty(
            "personalProperty", matchesPersonalProperty(quoteRequest.getPersonalProperty())),
        hasProperty(
            "studentInformation", matchesStudentInformation(quoteRequest.getStudentInformation())));
  }
}
