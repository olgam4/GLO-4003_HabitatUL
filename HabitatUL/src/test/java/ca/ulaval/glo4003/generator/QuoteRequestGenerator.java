package ca.ulaval.glo4003.generator;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequestView;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRequest;

public class QuoteRequestGenerator {
  private QuoteRequestGenerator() {}

  public static QuoteRequestView createQuoteRequestView() {
    return new QuoteRequestView();
  }

  public static QuoteRequestDto createQuoteRequestDto() {
    return new QuoteRequestDto();
  }

  public static QuoteRequest createValidQuoteRequest() {
    return new QuoteRequest();
  }
}
