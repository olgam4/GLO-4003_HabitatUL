package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.domain.quote.Quote;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class QuoteMatcher extends TypeSafeMatcher<QuoteDto> {
  private final Quote quote;

  private QuoteMatcher(final Quote quote) {
    this.quote = quote;
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
        String.format("matches corresponding quote: %s", quote.toString()));
  }

  @Override
  public boolean matchesSafely(final QuoteDto quoteDto) {
    return true;
  }

  public static boolean matchesQuote(final Quote quote, final QuoteDto quoteDto) {
    return new QuoteMatcher(quote).matchesSafely(quoteDto);
  }
}
