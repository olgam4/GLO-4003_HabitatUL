package ca.ulaval.glo4003.helper.calculator.form;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;

import static ca.ulaval.glo4003.helper.calculator.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.helper.calculator.form.building.BuildingGenerator.createBuildingRequest;
import static ca.ulaval.glo4003.helper.calculator.form.civilliability.CivilLiabilityGenerator.createCivilLiability;
import static ca.ulaval.glo4003.helper.calculator.form.civilliability.CivilLiabilityGenerator.createCivilLiabilityRequest;
import static ca.ulaval.glo4003.helper.calculator.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.helper.calculator.form.identity.IdentityGenerator.createIdentityRequest;
import static ca.ulaval.glo4003.helper.calculator.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.helper.calculator.form.location.LocationGenerator.createLocationRequest;
import static ca.ulaval.glo4003.helper.calculator.form.personalproperty.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.helper.calculator.form.personalproperty.PersonalPropertyGenerator.createPersonalPropertyRequest;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureDate;

public class QuoteFormGenerator {
  private QuoteFormGenerator() {}

  public static QuoteRequest createQuoteRequest() {
    return new QuoteRequest(
        createIdentityRequest(),
        createIdentityRequest(),
        createLocationRequest(),
        createFutureDate(),
        createBuildingRequest(),
        createPersonalPropertyRequest(),
        createCivilLiabilityRequest());
  }

  public static QuoteFormDto createQuoteFormDto() {
    return new QuoteFormDto(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createFutureDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }

  public static QuoteForm createQuoteForm() {
    return new QuoteForm(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createFutureDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }
}
