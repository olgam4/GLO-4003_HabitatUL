package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;

public class QuoteAssembler {
  public QuoteForm from(RequestQuoteDto requestQuoteDto) {
    return new QuoteForm(
        requestQuoteDto.getPersonalInformation(),
        requestQuoteDto.getAdditionalInsured(),
        requestQuoteDto.getLocation(),
        requestQuoteDto.getEffectiveDate(),
        requestQuoteDto.getBuilding(),
        requestQuoteDto.getPersonalProperty(),
        requestQuoteDto.getCivilLiability());
  }

  public QuoteDto from(Quote quote) {
    return new QuoteDto(
        quote.getQuoteId(),
        quote.getExpirationDate(),
        quote.getEffectivePeriod(),
        quote.getCoverageDetails(),
        quote.getPremiumDetails());
  }
}
