package ca.ulaval.glo4003.gateway.presentation.underwriting.quote;

import ca.ulaval.glo4003.gateway.presentation.coverage.CoverageViewAssembler;
import ca.ulaval.glo4003.gateway.presentation.underwriting.quote.request.QuoteRequest;
import ca.ulaval.glo4003.gateway.presentation.underwriting.quote.response.QuoteResponse;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;

public class QuoteViewAssembler {
  private CoverageViewAssembler coverageViewAssembler;

  public QuoteViewAssembler() {
    this.coverageViewAssembler = new CoverageViewAssembler();
  }

  public RequestQuoteDto from(QuoteRequest quoteRequest) {
    return new RequestQuoteDto(
        coverageViewAssembler.fromIdentityRequest(quoteRequest.getPersonalInformation()),
        coverageViewAssembler.fromIdentityRequest(quoteRequest.getAdditionalInsured()),
        coverageViewAssembler.fromLocationRequest(quoteRequest.getLocation()),
        quoteRequest.getEffectiveDate(),
        coverageViewAssembler.fromBuildingRequest(quoteRequest.getBuilding()),
        coverageViewAssembler.fromPersonalPropertyRequest(quoteRequest.getPersonalProperty()),
        coverageViewAssembler.fromCivilLiabilityRequest(quoteRequest.getCivilLiability()));
  }

  public QuoteResponse from(QuoteDto quoteDto) {
    return new QuoteResponse(
        quoteDto.getQuoteId(),
        quoteDto.getExpirationDate(),
        quoteDto.getEffectivePeriod(),
        quoteDto.getCoverageDetails(),
        quoteDto.getPremiumDetails());
  }
}
