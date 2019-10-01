package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.generator.quote.QuoteGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPersistedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotYetPersistedException;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.matcher.quote.QuoteMatcher.matchesQuote;
import static org.junit.Assert.assertThat;

public abstract class QuoteRepositoryIT {
  private QuoteRepository subject;
  private Quote quote;
  private QuoteId quoteId;

  @Before
  public void setUp() {
    subject = createSubject();
    quote = QuoteGenerator.createValidQuote();
    quoteId = quote.getQuoteId();
  }

  @Test(expected = QuoteNotFoundException.class)
  public void gettingQuoteById_withUnknownQuoteId_shouldThrow() {
    subject.getById(new QuoteId());
  }

  @Test
  public void creatingQuote_shouldPersistQuoteAsIs() {
    subject.create(quote);

    assertThat(subject.getById(quoteId), matchesQuote(quote));
  }

  @Test(expected = QuoteAlreadyPersistedException.class)
  public void creatingQuote_withAlreadyPersistedQuote_shouldThrow() {
    subject.create(quote);
    subject.create(quote);
  }

  @Test
  public void updatingQuote_shouldChangeAssociatedQuote() {
    subject.create(quote);

    Quote updatedQuote = QuoteGenerator.createValidQuoteWithId(quoteId);
    subject.update(updatedQuote);

    assertThat(subject.getById(quoteId), matchesQuote(updatedQuote));
  }

  @Test(expected = QuoteNotYetPersistedException.class)
  public void updatingQuote_withNotYetPersistedQuote_shouldThrow() {
    subject.update(quote);
  }

  protected abstract QuoteRepository createSubject();
}
