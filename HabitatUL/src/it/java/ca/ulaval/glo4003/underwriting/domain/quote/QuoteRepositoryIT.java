package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.generator.QuoteGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPersistedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotYetPersistedException;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.matcher.QuoteMatcher.matchesQuote;

public abstract class QuoteRepositoryIT {
  private QuoteRepository quoteRepository;
  private Quote quote;
  private QuoteId quoteId;

  @Before
  public void setUp() {
    quoteRepository = createRepository();
    quote = QuoteGenerator.createValidQuote();
    quoteId = quote.getQuoteId();
  }

  @Test(expected = QuoteNotFoundException.class)
  public void gettingQuoteById_withUnknownQuoteId_shouldThrow() {
    quoteRepository.getById(new QuoteId());
  }

  @Test
  public void creatingQuote_shouldPersistQuoteAsIs() {
    quoteRepository.create(quote);

    matchesQuote(quote, quoteRepository.getById(quoteId));
  }

  @Test(expected = QuoteAlreadyPersistedException.class)
  public void creatingQuote_withAlreadyPersistedQuote_shouldThrow() {
    quoteRepository.create(quote);
    quoteRepository.create(quote);
  }

  @Test
  public void updatingQuote_shouldChangeAssociatedQuote() {
    quoteRepository.create(quote);

    Quote updatedQuote = QuoteGenerator.createValidQuoteWithId(quoteId);
    quoteRepository.update(updatedQuote);

    matchesQuote(updatedQuote, quoteRepository.getById(quoteId));
  }

  @Test(expected = QuoteNotYetPersistedException.class)
  public void updatingQuote_withNotYetPersistedQuote_shouldThrow() {
    quoteRepository.update(quote);
  }

  protected abstract QuoteRepository createRepository();
}
