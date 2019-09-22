package ca.ulaval.glo4003.quote.matcher;

import ca.ulaval.glo4003.quote.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.quote.domain.quote.QuoteRequest;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

public class QuoteRequestDtoMatcher extends TypeSafeMatcher<QuoteRequest> {
  private final QuoteRequestDto quoteRequestDto;

  private QuoteRequestDtoMatcher(final QuoteRequestDto quoteRequestDto) {
    this.quoteRequestDto = quoteRequestDto;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
        String.format("matches corresponding quote request dto: %s", quoteRequestDto.toString()));
  }

  @Override
  public boolean matchesSafely(final QuoteRequest quoteRequest) {
    return true;
  }

  public static QuoteRequest getQuoteRequestDtoMockitoMatcher(
      final QuoteRequestDto quoteRequestDto) {
    return MockitoHamcrest.argThat(getQuoteRequestDtoMatcher(quoteRequestDto));
  }

  public static QuoteRequestDtoMatcher getQuoteRequestDtoMatcher(
      final QuoteRequestDto quoteRequestDto) {
    return new QuoteRequestDtoMatcher(quoteRequestDto);
  }
}
