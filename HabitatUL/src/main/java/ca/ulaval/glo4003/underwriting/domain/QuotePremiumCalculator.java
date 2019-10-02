package ca.ulaval.glo4003.underwriting.domain;

import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public interface QuotePremiumCalculator {
  Premium computeQuotePremium(QuoteForm quoteForm);
}
