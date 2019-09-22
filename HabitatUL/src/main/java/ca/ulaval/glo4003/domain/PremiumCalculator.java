package ca.ulaval.glo4003.domain;

import ca.ulaval.glo4003.domain.commons.Premium;
import ca.ulaval.glo4003.domain.quote.QuoteRequest;

public interface PremiumCalculator {
  Premium computeQuotePremium(QuoteRequest quoteRequest);
}
