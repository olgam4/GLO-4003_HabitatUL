package ca.ulaval.glo4003.quote.domain;

import ca.ulaval.glo4003.quote.domain.commons.Premium;
import ca.ulaval.glo4003.quote.domain.quote.QuoteRequest;

public interface PremiumCalculator {
  Premium computeQuotePremium(QuoteRequest quoteRequest);
}
