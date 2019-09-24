package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.gateway.presentation.quote.view.request.QuoteRequestView;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

public class QuoteRequestDtoMatcher extends TypeSafeMatcher<QuoteRequestDto> {
  private final QuoteRequestView quoteRequestView;

  private QuoteRequestDtoMatcher(final QuoteRequestView quoteRequestView) {
    this.quoteRequestView = quoteRequestView;
  }

  public static QuoteRequestDto getQuoteRequestDtoMockitoMatcher(
      final QuoteRequestView quoteRequestView) {
    return MockitoHamcrest.argThat(getQuoteRequestDtoMatcher(quoteRequestView));
  }

  public static QuoteRequestDtoMatcher getQuoteRequestDtoMatcher(
      final QuoteRequestView quoteRequestView) {
    return new QuoteRequestDtoMatcher(quoteRequestView);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
        String.format(
            "matches corresponding underwriting request dto: %s", quoteRequestView.toString()));
  }

  @Override
  public boolean matchesSafely(final QuoteRequestDto quoteRequestDto) {
    return true;
  }
}
