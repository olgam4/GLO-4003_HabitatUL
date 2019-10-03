package ca.ulaval.glo4003.matcher.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class QuoteMatcher extends TypeSafeMatcher<Quote> {
  private final Quote quote;

  private QuoteMatcher(final Quote quote) {
    this.quote = quote;
  }

  public static Matcher<Quote> matchesQuote(final Quote quote) {
    return new QuoteMatcher(quote);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(String.format("matches corresponding quote: %s", quote.toString()));
  }

  @Override
  public boolean matchesSafely(final Quote quote) {
    return quote.getQuoteId().equals(quote.getQuoteId())
        && quote.getExpirationDate().equals(this.quote.getExpirationDate())
        && quote.isPurchased() == this.quote.isPurchased();
  }
}
