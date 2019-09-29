package ca.ulaval.glo4003.underwriting.domain;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteForm;

public interface PremiumCalculator {
  Premium computeQuotePremium(QuoteForm quoteForm);
}
