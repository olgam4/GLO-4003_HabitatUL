package ca.ulaval.glo4003.management.persistence.user;

import ca.ulaval.glo4003.management.domain.user.QuoteRegistry;
import ca.ulaval.glo4003.management.domain.user.QuoteRegistryIT;

public class InMemoryQuoteRegistryIT extends QuoteRegistryIT {
  @Override
  protected QuoteRegistry createSubject() {
    return new InMemoryQuoteRegistry();
  }
}
