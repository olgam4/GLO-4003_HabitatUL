package ca.ulaval.glo4003.underwriting.persistence.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryQuoteRepository implements QuoteRepository {
  private final Map<QuoteId, Quote> quotes = new HashMap<>();

  @Override
  public Quote getById(QuoteId quoteId) throws QuoteNotFoundException {
    if (isExistingQuote(quoteId)) return quotes.get(quoteId);

    throw new QuoteNotFoundException();
  }

  @Override
  public void create(Quote quote) throws QuoteAlreadyCreatedException {
    QuoteId quoteId = quote.getQuoteId();

    if (isExistingQuote(quoteId)) throw new QuoteAlreadyCreatedException();

    quotes.put(quoteId, quote);
  }

  @Override
  public void update(Quote quote) throws QuoteNotFoundException {
    QuoteId quoteId = quote.getQuoteId();

    if (!isExistingQuote(quoteId)) throw new QuoteNotFoundException();

    quotes.put(quoteId, quote);
  }

  private boolean isExistingQuote(QuoteId quoteId) {
    return quotes.containsKey(quoteId);
  }
}
