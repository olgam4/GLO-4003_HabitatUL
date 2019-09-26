package ca.ulaval.glo4003.underwriting.persistence.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyPersistedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotYetPersistedException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryQuoteRepository implements QuoteRepository {
  private final Map<QuoteId, Quote> quotes = new HashMap<>();

  @Override
  public Quote getById(QuoteId quoteId) {
    if (isExistingQuote(quoteId)) return quotes.get(quoteId);

    throw new QuoteNotFoundException();
  }

  @Override
  public void create(Quote quote) {
    QuoteId quoteId = quote.getQuoteId();

    if (isExistingQuote(quoteId)) throw new QuoteAlreadyPersistedException();

    quotes.put(quoteId, quote);
  }

  @Override
  public void update(Quote quote) {
    QuoteId quoteId = quote.getQuoteId();

    if (!isExistingQuote(quoteId)) throw new QuoteNotYetPersistedException();

    quotes.put(quoteId, quote);
  }

  private boolean isExistingQuote(QuoteId quoteId) {
    return quotes.containsKey(quoteId);
  }
}
