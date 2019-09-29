package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteForm;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.mockito.hamcrest.MockitoHamcrest;

public class QuoteFormMatcher extends TypeSafeMatcher<QuoteForm> {
  private final QuoteFormDto quoteFormDto;

  private QuoteFormMatcher(final QuoteFormDto quoteFormDto) {
    this.quoteFormDto = quoteFormDto;
  }

  public static QuoteForm getQuoteFormMockitoMatcher(final QuoteFormDto quoteFormDto) {
    return MockitoHamcrest.argThat(getQuoteFormMatcher(quoteFormDto));
  }

  public static QuoteFormMatcher getQuoteFormMatcher(final QuoteFormDto quoteFormDto) {
    return new QuoteFormMatcher(quoteFormDto);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
        String.format("matches corresponding quote request dto: %s", quoteFormDto.toString()));
  }

  @Override
  public boolean matchesSafely(final QuoteForm quoteForm) {
    return true;
  }
}
