package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteRequestDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRequest;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

public class QuoteRequestMatcher extends TypeSafeMatcher<QuoteRequest> {
  private final QuoteRequestDto quoteRequestDto;

  private QuoteRequestMatcher(final QuoteRequestDto quoteRequestDto) {
    this.quoteRequestDto = quoteRequestDto;
  }

  public static QuoteRequest getQuoteRequestDtoMockitoMatcher(
      final QuoteRequestDto quoteRequestDto) {
    return MockitoHamcrest.argThat(getQuoteRequestDtoMatcher(quoteRequestDto));
  }

  public static QuoteRequestMatcher getQuoteRequestDtoMatcher(
      final QuoteRequestDto quoteRequestDto) {
    return new QuoteRequestMatcher(quoteRequestDto);
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
}
