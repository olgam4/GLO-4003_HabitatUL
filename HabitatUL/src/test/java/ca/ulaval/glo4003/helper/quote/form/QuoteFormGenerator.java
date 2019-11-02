package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

import static ca.ulaval.glo4003.helper.TemporalGenerator.createFutureDate;
import static ca.ulaval.glo4003.helper.quote.form.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.helper.quote.form.BuildingGenerator.createBuildingRequest;
import static ca.ulaval.glo4003.helper.quote.form.CivilLiabilityGenerator.createCivilLiability;
import static ca.ulaval.glo4003.helper.quote.form.CivilLiabilityGenerator.createCivilLiabilityRequest;
import static ca.ulaval.glo4003.helper.quote.form.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.helper.quote.form.IdentityGenerator.createIdentityRequest;
import static ca.ulaval.glo4003.helper.quote.form.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.helper.quote.form.LocationGenerator.createLocationRequest;
import static ca.ulaval.glo4003.helper.quote.form.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.helper.quote.form.PersonalPropertyGenerator.createPersonalPropertyRequest;

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
