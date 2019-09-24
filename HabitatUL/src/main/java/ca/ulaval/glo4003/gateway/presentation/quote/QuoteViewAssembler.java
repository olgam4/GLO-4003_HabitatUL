package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequestView;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;

public class QuoteViewAssembler {
  public QuoteRequestDto from(QuoteRequestView quoteRequestView) {
    return new QuoteRequestDto();
  }
}
