package ca.ulaval.glo4003.underwriting.generator;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRequest;

public class QuoteRequestGenerator {
  private QuoteRequestGenerator() {}

  public static QuoteRequest createValidQuoteRequest() {
    return new QuoteRequest();
  }

  public static QuoteRequestDto createDto() {
    return new QuoteRequestDto();
  }
}
