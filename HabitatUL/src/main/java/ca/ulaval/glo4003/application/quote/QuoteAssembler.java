package ca.ulaval.glo4003.application.quote;

import ca.ulaval.glo4003.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.domain.quote.Quote;
import ca.ulaval.glo4003.domain.quote.QuoteRequest;

public class QuoteAssembler {
  public static QuoteDto from(Quote quote) {
    return new QuoteDto();
  }

  public static QuoteRequest from(QuoteRequestDto quoteRequestDto) {
    return new QuoteRequest();
  }
}
