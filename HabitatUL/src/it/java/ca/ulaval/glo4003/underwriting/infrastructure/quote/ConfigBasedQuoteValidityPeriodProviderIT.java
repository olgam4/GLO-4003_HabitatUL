package ca.ulaval.glo4003.underwriting.infrastructure.quote;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProviderIT;

public class ConfigBasedQuoteValidityPeriodProviderIT extends QuoteValidityPeriodProviderIT {
  @Override
  public QuoteValidityPeriodProvider createSubject() {
    return new ConfigBasedQuoteValidityPeriodProvider();
  }
}
