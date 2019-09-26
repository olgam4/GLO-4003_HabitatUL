package ca.ulaval.glo4003.underwriting.persistence.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepository;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRepositoryIT;

public class InMemoryQuoteRepositoryIT extends QuoteRepositoryIT {
  @Override
  protected QuoteRepository createSubject() {
    return new InMemoryQuoteRepository();
  }
}
