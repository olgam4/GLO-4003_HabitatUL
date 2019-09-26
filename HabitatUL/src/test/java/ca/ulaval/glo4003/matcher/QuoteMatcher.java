package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class QuoteMatcher extends TypeSafeMatcher<Quote> {
      private final Quote quote;

  private QuoteMatcher(final Quote quote) {
    this.quote = quote;
  }

  public static boolean matchesQuote(final Quote expected, final Quote observed) {
    return new QuoteMatcher(expected).matchesSafely(observed);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(
        String.format("matches corresponding quote: %s", quote.toString()));
  }

  @Override
  public boolean matchesSafely(final Quote quote) {
    return true;
  }
}
