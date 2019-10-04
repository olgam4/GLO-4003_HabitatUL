package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.request.*;
import ca.ulaval.glo4003.gateway.presentation.quote.response.QuoteResponse;
import ca.ulaval.glo4003.shared.domain.Amount;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.shared.domain.Period;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;

import java.util.Optional;

public class QuoteViewAssembler {
  public QuoteFormDto from(QuoteRequest quoteRequest) {
    return new QuoteFormDto(
        from(quoteRequest.getIdentity()),
        from(quoteRequest.getLocation()),
        quoteRequest.getEffectiveDate(),
        from(quoteRequest.getBuilding()),
        from(quoteRequest.getPersonalProperty()));
  }

  private Identity from(IdentityView identityView) {
    return new Identity(
        identityView.getFirstName(),
        identityView.getLastName(),
        identityView.getBirthDate(),
        identityView.getGender());
  }

  private Location from(LocationView locationView) {
    return new Location(
        locationView.getPostalCode(),
        locationView.getStreetNumber(),
        locationView.getApartmentNumber(),
        locationView.getFloor());
  }

  private Building from(BuildingView buildingView) {
    return new Building(
        buildingView.getNumberOfUnits(), buildingView.getPreventionSystems(), Optional.empty());
  }

  private PersonalProperty from(PersonalPropertyView personalPropertyView) {
    Amount coverageAmount = personalPropertyView.getCoverageAmount();
    Animals animals = personalPropertyView.getAnimals();
    return new PersonalProperty(coverageAmount, animals);
  }

  public QuoteResponse from(QuoteDto quoteDto) {
    QuoteId quoteId = quoteDto.getQuoteId();
    Premium premium = quoteDto.getPremium();
    Period effectivePeriod = quoteDto.getEffectivePeriod();
    DateTime expirationDate = quoteDto.getExpirationDate();
    return new QuoteResponse(quoteId, premium, effectivePeriod, expirationDate);
  }
}
