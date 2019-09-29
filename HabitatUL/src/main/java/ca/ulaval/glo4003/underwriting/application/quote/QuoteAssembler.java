package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteForm;

public class QuoteAssembler {
  public static QuoteDto from(Quote quote) {
    return new QuoteDto(quote.getQuoteId());
  }

  public static QuoteForm from(QuoteFormDto quoteFormDto) {
    return new QuoteForm();
  }
}
