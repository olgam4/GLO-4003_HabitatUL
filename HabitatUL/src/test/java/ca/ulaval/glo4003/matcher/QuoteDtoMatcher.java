package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class QuoteDtoMatcher extends TypeSafeMatcher<QuoteDto> {
  private final Quote quote;

  private QuoteDtoMatcher(final Quote quote) {
    this.quote = quote;
  }

  public static Matcher<QuoteDto> matchesQuote(final Quote quote) {
    return new QuoteDtoMatcher(quote);
  }

  @Override
  public void describeTo(final Description description) {
    description.appendText(String.format("matches corresponding quote: %s", quote.toString()));
  }

  @Override
  public boolean matchesSafely(final QuoteDto quoteDto) {
    return quoteDto.getQuoteId() == this.quote.getQuoteId();
  }
}
