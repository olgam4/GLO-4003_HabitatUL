package ca.ulaval.glo4003.underwriting.infrastructure.premium;

import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.premium.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class DummyPremiumCalculator implements PremiumCalculator {
  private Premium premium;

  public DummyPremiumCalculator(Premium dummyPremium) {
    this.premium = dummyPremium;
  }

  @Override
  public Premium computeQuotePremium(QuoteForm quoteForm) {
    return premium;
  }
}
