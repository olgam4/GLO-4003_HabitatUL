package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteAlreadyCreatedException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.QuoteNotFoundException;

public interface QuoteRepository {
  Quote getById(QuoteId quoteId) throws QuoteNotFoundException;

  void create(Quote quote) throws QuoteAlreadyCreatedException;

  void update(Quote quote) throws QuoteNotFoundException;
}
