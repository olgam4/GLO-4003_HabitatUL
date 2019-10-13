package ca.ulaval.glo4003.administration.persistence.user;

import ca.ulaval.glo4003.administration.domain.user.QuoteRegistry;
import ca.ulaval.glo4003.administration.domain.user.QuoteRegistryIT;

public class InMemoryQuoteRegistryIT extends QuoteRegistryIT {
  @Override
  protected QuoteRegistry createSubject() {
    return new InMemoryQuoteRegistry();
  }
}
