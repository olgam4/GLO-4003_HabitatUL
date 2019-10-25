package ca.ulaval.glo4003.underwriting.infrastructure.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProviderIT;

public class ConfigBasedQuoteEffectivePeriodProviderIT extends QuoteEffectivePeriodProviderIT {
  @Override
  public QuoteEffectivePeriodProvider createSubject() {
    return new ConfigBasedQuoteEffectivePeriodProvider();
  }
}
