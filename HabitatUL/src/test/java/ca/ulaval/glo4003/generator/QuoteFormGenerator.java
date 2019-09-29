package ca.ulaval.glo4003.generator;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteForm;

public class QuoteFormGenerator {
  private QuoteFormGenerator() {}

  public static QuoteRequest createQuoteRequest() {
    return new QuoteRequest();
  }

  public static QuoteFormDto createQuoteFormDto() {
    return new QuoteFormDto();
  }

  public static QuoteForm createValidQuoteForm() {
    return new QuoteForm();
  }
}
