package ca.ulaval.glo4003.generator.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

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
