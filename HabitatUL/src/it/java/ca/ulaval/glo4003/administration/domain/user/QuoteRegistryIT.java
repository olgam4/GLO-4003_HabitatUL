package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.administration.domain.user.exception.KeyNotFoundError;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class QuoteRegistryIT {
  private static final String USER_KEY = Faker.instance().book().author();
  private static final String NOT_EXISTING_USER_KEY = Faker.instance().food().fruit();
  private static final String QUOTE_KEY = Faker.instance().cat().name();
  private static final String NOT_EXISTING_QUOTE_KEY = Faker.instance().dragonBall().character();

  private QuoteRegistry subject;

  @Before
  public void setUp() {
    subject = createSubject();
    subject.register(USER_KEY, QUOTE_KEY);
  }

  @Test
  public void gettingUserKey_withRegisteredQuoteKey_shouldReturnMappedUserKey() {
    assertEquals(USER_KEY, subject.getUserKey(QUOTE_KEY));
  }

  @Test(expected = KeyNotFoundError.class)
  public void gettingUserKey_withNotExistingQuoteKey_shouldThrow() {
    subject.getUserKey(NOT_EXISTING_QUOTE_KEY);
  }

  @Test
  public void gettingQuoteKeys_withRegisteredUserKey_shouldReturnAllMappedQuoteKeys() {
    List<String> quoteKeys = subject.getQuoteKeys(USER_KEY);

    assertEquals(Arrays.asList(QUOTE_KEY), quoteKeys);
  }

  @Test
  public void gettingQuoteKeys_withNotExistingUserKey_shouldReturnEmptyList() {
    List<String> quoteKeys = subject.getQuoteKeys(NOT_EXISTING_USER_KEY);

    assertEquals(0, quoteKeys.size());
  }

  protected abstract QuoteRegistry createSubject();
}
