package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuoteAssembler {
  public QuoteDto from(Quote quote) {
    return new QuoteDto(
        quote.getQuoteId(),
        quote.getPrice(),
        quote.getEffectivePeriod(),
        quote.getExpirationDate());
  }

  public QuoteForm from(QuoteFormDto quoteFormDto) {
    return new QuoteForm(
        quoteFormDto.getIdentity(),
        quoteFormDto.getLocation(),
        quoteFormDto.getEffectiveDate(),
        quoteFormDto.getBuilding(),
        quoteFormDto.getPersonalProperty());
  }
}
