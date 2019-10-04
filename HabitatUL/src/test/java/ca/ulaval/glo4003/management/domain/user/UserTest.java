package ca.ulaval.glo4003.management.domain.user;

import ca.ulaval.glo4003.generator.user.UserGenerator;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserTest {
  private static final QuoteKey QUOTE_KEY = new QuoteKey(Faker.instance().internet().uuid());

  private User subject;

  @Before
  public void setUp() {
    subject = UserGenerator.createUser();
  }

  @Test
  public void associatingQuote_withoutPriorQuote_shouldAddQuote() {
    subject.associate(QUOTE_KEY);

    assertEquals(Arrays.asList(QUOTE_KEY), subject.getQuotes());
  }

  @Test
  public void associatingQuote_withPriorQuotes_shouldAddQuote() {
    subject.associate(QUOTE_KEY);

    QuoteKey anotherQuoteKey = new QuoteKey(Faker.instance().internet().uuid());
    subject.associate(anotherQuoteKey);

    assertEquals(Arrays.asList(QUOTE_KEY, anotherQuoteKey), subject.getQuotes());
  }

  @Test
  public void gettingQuotes_shouldBeImmutable() {
    List<QuoteKey> quotes = subject.getQuotes();

    quotes.add(QUOTE_KEY);

    assertNotEquals(quotes, subject.getQuotes());
  }
}
