package ca.ulaval.glo4003.underwriting.infrastructure;

import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class DummyQuotePremiumCalculator implements QuotePremiumCalculator {
  private Premium premium;

  public DummyQuotePremiumCalculator(Premium dummyPremium) {
    this.premium = dummyPremium;
  }

  @Override
  public Premium computeQuotePremium(QuoteForm quoteForm) {
    return premium;
  }
}
