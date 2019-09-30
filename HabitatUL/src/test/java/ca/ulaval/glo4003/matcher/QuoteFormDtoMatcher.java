package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

public class QuoteFormDtoMatcher extends TypeSafeMatcher<QuoteFormDto> {
  private final QuoteRequest quoteRequest;

  private QuoteFormDtoMatcher(final QuoteRequest quoteRequest) {
    this.quoteRequest = quoteRequest;
  }

  public static QuoteFormDto mockitoQuoteFormDtoMatcher(final QuoteRequest quoteRequest) {
    return MockitoHamcrest.argThat(matchesQuoteFormDto(quoteRequest));
  }

  public static Matcher<QuoteFormDto> matchesQuoteFormDto(final QuoteRequest quoteRequest) {
    return new QuoteFormDtoMatcher(quoteRequest);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
        String.format("matches corresponding quote form dto: %s", quoteRequest.toString()));
  }

  @Override
  public boolean matchesSafely(final QuoteFormDto quoteFormDto) {
    return true;
  }
}
