package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.underwriting.domain.Premium;

public class QuoteFactory {
  private QuoteValidityPeriodProvider quoteValidityPeriodProvider;
  private ClockProvider clockProvider;

  public QuoteFactory(
      QuoteValidityPeriodProvider quoteValidityPeriodProvider, ClockProvider clockProvider) {
    this.quoteValidityPeriodProvider = quoteValidityPeriodProvider;
    this.clockProvider = clockProvider;
  }

  public Quote create(Premium premium, QuoteForm quoteForm) {
    QuoteId quoteId = new QuoteId();
    Date expirationDate =
        Date.now(clockProvider.getClock())
            .plus(quoteValidityPeriodProvider.getQuoteValidityPeriod());
    return new Quote(quoteId, premium, quoteForm, expirationDate, false, clockProvider);
  }
}
