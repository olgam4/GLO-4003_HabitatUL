package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.helper.quote.QuoteBuilder;
import ca.ulaval.glo4003.helper.quote.QuoteGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.matcher.QuoteMatcher.matchesQuote;
import static org.junit.Assert.assertThat;

public abstract class QuoteRepositoryIT {
  private QuoteRepository subject;
  private Quote quote;
  private QuoteId quoteId;

  @Before
  public void setUp() {
    subject = createSubject();
    quote = QuoteGenerator.createQuote();
    quoteId = quote.getQuoteId();
  }

  @Test(expected = QuoteNotFoundException.class)
  public void gettingQuoteById_withUnknownQuoteId_shouldThrow() throws QuoteNotFoundException {
    subject.getById(new QuoteId());
  }

  @Test
  public void creatingQuote_shouldPersistQuoteAsIs()
      throws QuoteAlreadyCreatedException, QuoteNotFoundException {
    subject.create(quote);

    assertThat(subject.getById(quoteId), matchesQuote(quote));
  }

  @Test(expected = QuoteAlreadyCreatedException.class)
  public void creatingQuote_withAlreadyPersistedQuote_shouldThrow()
      throws QuoteAlreadyCreatedException {
    subject.create(quote);
    subject.create(quote);
  }

  @Test
  public void updatingQuote_shouldChangeAssociatedQuote()
      throws QuoteNotFoundException, QuoteAlreadyCreatedException {
    subject.create(quote);

    Quote updatedQuote = QuoteBuilder.aQuote().withId(quoteId).build();
    subject.update(updatedQuote);

    assertThat(subject.getById(quoteId), matchesQuote(updatedQuote));
  }

  @Test(expected = QuoteNotFoundException.class)
  public void updatingQuote_withNotYetPersistedQuote_shouldThrow() throws QuoteNotFoundException {
    subject.update(quote);
  }

  protected abstract QuoteRepository createSubject();
}
