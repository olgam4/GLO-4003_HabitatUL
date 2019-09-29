package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;

public class QuoteViewAssembler {
  public QuoteFormDto from(QuoteRequest quoteRequest) {
    return new QuoteFormDto();
  }
}
