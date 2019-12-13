package ca.ulaval.glo4003.underwriting.helper.quote;

import ca.ulaval.glo4003.gateway.presentation.underwriting.quote.request.QuoteRequest;
import ca.ulaval.glo4003.shared.helper.EnumSampler;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAssembler;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteStatus;

import static ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.coverage.helper.form.QuoteFormGenerator.createQuoteForm;
import static ca.ulaval.glo4003.coverage.helper.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.coverage.helper.form.building.BuildingGenerator.createBuildingRequest;
import static ca.ulaval.glo4003.coverage.helper.form.civilliability.CivilLiabilityGenerator.createCivilLiability;
import static ca.ulaval.glo4003.coverage.helper.form.civilliability.CivilLiabilityGenerator.createCivilLiabilityRequest;
import static ca.ulaval.glo4003.coverage.helper.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.coverage.helper.form.identity.IdentityGenerator.createIdentityRequest;
import static ca.ulaval.glo4003.coverage.helper.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.coverage.helper.form.location.LocationGenerator.createLocationRequest;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.coverage.helper.form.personalproperty.PersonalPropertyGenerator.createPersonalPropertyRequest;
import static ca.ulaval.glo4003.coverage.helper.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.*;

public class QuoteGenerator {
  private QuoteGenerator() {}

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

  public static RequestQuoteDto createRequestQuoteDto() {
    return new RequestQuoteDto(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createFutureDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }

  public static QuoteDto createQuoteDto() {
    return new QuoteAssembler().from(createQuote());
  }

  public static Quote createQuote() {
    return new Quote(
        createQuoteId(),
        createQuoteStatus(),
        createQuoteForm(),
        createFutureDateTime(),
        createPeriod(),
        createCoverageDetails(),
        createPremiumDetails(),
        getClockProvider());
  }

  public static QuoteId createQuoteId() {
    return new QuoteId();
  }

  public static QuoteStatus createQuoteStatus() {
    return EnumSampler.sample(QuoteStatus.class);
  }
}
