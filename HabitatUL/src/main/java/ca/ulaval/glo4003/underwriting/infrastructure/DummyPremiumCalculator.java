package ca.ulaval.glo4003.underwriting.infrastructure;

import ca.ulaval.glo4003.shared.domain.Premium;
import ca.ulaval.glo4003.underwriting.domain.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRequest;

public class DummyPremiumCalculator implements PremiumCalculator {
  private Premium premium;

  public DummyPremiumCalculator(Premium dummyPremium) {
    this.premium = dummyPremium;
  }

  @Override
  public Premium computeQuotePremium(QuoteRequest quoteRequest) {
    return premium;
  }
}