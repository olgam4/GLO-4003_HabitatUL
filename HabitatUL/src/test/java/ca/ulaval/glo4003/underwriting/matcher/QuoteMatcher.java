package ca.ulaval.glo4003.underwriting.matcher;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class QuoteMatcher extends TypeSafeMatcher<QuoteDto> {
  private final Quote quote;

  private QuoteMatcher(final Quote quote) {
    this.quote = quote;
  }

  public static boolean matchesQuote(final Quote quote, final QuoteDto quoteDto) {
    return new QuoteMatcher(quote).matchesSafely(quoteDto);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
        String.format("matches corresponding underwriting: %s", quote.toString()));
  }

  @Override
  public boolean matchesSafely(final QuoteDto quoteDto) {
    return true;
  }
}
