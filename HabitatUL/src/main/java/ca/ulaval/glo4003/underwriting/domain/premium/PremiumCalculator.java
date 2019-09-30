package ca.ulaval.glo4003.underwriting.domain.premium;

import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public interface PremiumCalculator {
  Premium computeQuotePremium(QuoteForm quoteForm);
}
