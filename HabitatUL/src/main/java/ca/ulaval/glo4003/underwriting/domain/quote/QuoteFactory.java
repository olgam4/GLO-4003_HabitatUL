package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

public class QuoteFactory {
  private QuoteValidityPeriodProvider quoteValidityPeriodProvider;
  private QuoteEffectivePeriodProvider quoteEffectivePeriodProvider;
  private ClockProvider clockProvider;

  public QuoteFactory(
      QuoteValidityPeriodProvider quoteValidityPeriodProvider,
      QuoteEffectivePeriodProvider quoteEffectivePeriodProvider,
      ClockProvider clockProvider) {
    this.quoteValidityPeriodProvider = quoteValidityPeriodProvider;
    this.quoteEffectivePeriodProvider = quoteEffectivePeriodProvider;
    this.clockProvider = clockProvider;
  }

  public Quote create(PremiumDetails premiumDetails, QuoteForm quoteForm) {
    QuoteId quoteId = new QuoteId();
    DateTime expirationDate =
        DateTime.now(clockProvider.getClock())
            .plus(quoteValidityPeriodProvider.getQuoteValidityPeriod());
    Date effectivePeriodStartDate = quoteForm.getEffectiveDate();
    Date effectivePeriodEndDate =
        quoteForm.getEffectiveDate().plus(quoteEffectivePeriodProvider.getQuoteEffectivePeriod());
    Period effectivePeriod = new Period(effectivePeriodStartDate, effectivePeriodEndDate);
    return new Quote(
        quoteId, quoteForm, expirationDate, effectivePeriod, premiumDetails, false, clockProvider);
  }
}
