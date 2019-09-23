package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRequest;

public class QuoteAssembler {
  public static QuoteDto from(Quote quote) {
    return new QuoteDto();
  }

  public static QuoteRequest from(QuoteRequestDto quoteRequestDto) {
    return new QuoteRequest();
  }
}
