package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;

public class QuoteAssembler {
  public QuoteForm from(QuoteFormDto quoteFormDto) {
    return new QuoteForm(
        quoteFormDto.getPersonalInformation(),
        quoteFormDto.getAdditionalInsured(),
        quoteFormDto.getLocation(),
        quoteFormDto.getEffectiveDate(),
        quoteFormDto.getBuilding(),
        quoteFormDto.getPersonalProperty(),
        quoteFormDto.getCivilLiability());
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
