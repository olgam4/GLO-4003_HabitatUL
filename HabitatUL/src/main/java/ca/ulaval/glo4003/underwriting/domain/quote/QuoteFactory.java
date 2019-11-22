package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

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

  public Quote create(
      QuoteForm quoteForm, CoverageDetails coverageDetails, PremiumDetails premiumDetails) {
    QuoteId quoteId = new QuoteId();
    DateTime expirationDate = computeExpirationDate();
    Period effectivePeriod = computeEffectivePeriod(quoteForm);
    return new Quote(
        quoteId,
        quoteForm,
        expirationDate,
        effectivePeriod,
        coverageDetails,
        premiumDetails,
        false,
        clockProvider);
  }

  private DateTime computeExpirationDate() {
    return DateTime.now(clockProvider.getClock())
        .plus(quoteValidityPeriodProvider.getQuoteValidityPeriod());
  }

  private Period computeEffectivePeriod(QuoteForm quoteForm) {
    Date effectivePeriodStartDate = quoteForm.getEffectiveDate();
    Date effectivePeriodEndDate =
        quoteForm
            .getEffectiveDate()
            .plus(quoteEffectivePeriodProvider.getQuoteEffectivePeriod())
            .minus(java.time.Period.ofDays(1));
    return new Period(effectivePeriodStartDate, effectivePeriodEndDate);
  }
}
