package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuoteFactory {
  private QuoteValidityPeriodProvider quoteValidityPeriodProvider;
  private ClockProvider clockProvider;

  public QuoteFactory(
      QuoteValidityPeriodProvider quoteValidityPeriodProvider, ClockProvider clockProvider) {
    this.quoteValidityPeriodProvider = quoteValidityPeriodProvider;
    this.clockProvider = clockProvider;
  }

  public Quote create(Money price, QuoteForm quoteForm) {
    QuoteId quoteId = new QuoteId();
    DateTime expirationDate =
        DateTime.now(clockProvider.getClock())
            .plus(quoteValidityPeriodProvider.getQuoteValidityPeriod());
    return new Quote(quoteId, price, quoteForm, expirationDate, false, clockProvider);
  }
}
