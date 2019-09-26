package ca.ulaval.glo4003.underwriting.persistence.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepositoryIT;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;

public class InMemoryQuoteRepositoryIT extends QuoteRepositoryIT {
  @Override
  protected QuoteRepository createRepository() {
    return new InMemoryQuoteRepository();
  }
}
