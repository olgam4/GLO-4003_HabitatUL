package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

import static ca.ulaval.glo4003.generator.quote.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.generator.quote.form.identity.IdentityGenerator.createIdentityView;
import static ca.ulaval.glo4003.generator.quote.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.generator.quote.form.location.LocationGenerator.createLocationView;

public class QuoteFormGenerator {
  private QuoteFormGenerator() {}

  public static QuoteRequest createQuoteRequest() {
    return new QuoteRequest(createIdentityView(), createLocationView());
  }

  public static QuoteFormDto createQuoteFormDto() {
    return new QuoteFormDto(createIdentity(), createLocation());
  }

  public static QuoteForm createValidQuoteForm() {
    return new QuoteForm(createIdentity(), createLocation());
  }
}
