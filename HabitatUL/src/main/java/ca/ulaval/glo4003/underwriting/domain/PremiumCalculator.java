package ca.ulaval.glo4003.underwriting.domain;

import ca.ulaval.glo4003.shared.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRequest;

public interface PremiumCalculator {
  Premium computeQuotePremium(QuoteRequest quoteRequest);
}
