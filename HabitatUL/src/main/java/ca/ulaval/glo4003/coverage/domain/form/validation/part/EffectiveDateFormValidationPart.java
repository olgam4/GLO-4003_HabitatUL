package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.QuoteEffectiveDateError;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;

public class EffectiveDateFormValidationPart implements QuoteFormValidationPart {
  private QuoteEffectivePeriodProvider quoteEffectivePeriodProvider;
  private ClockProvider clockProvider;

  public EffectiveDateFormValidationPart(
      QuoteEffectivePeriodProvider quoteEffectivePeriodProvider, ClockProvider clockProvider) {
    this.quoteEffectivePeriodProvider = quoteEffectivePeriodProvider;
    this.clockProvider = clockProvider;
  }

  @Override
  public void validate(QuoteForm form) {
    if (isInvalidEffectiveDate(form.getEffectiveDate())) {
      throw new QuoteEffectiveDateError();
    }
  }

  private boolean isInvalidEffectiveDate(Date effectiveDate) {
    Date minDate = Date.now(clockProvider.getClock());
    Date maxDate = minDate.plus(quoteEffectivePeriodProvider.getQuoteEffectivePeriod());
    boolean tooSoon = maxDate.isBefore(effectiveDate);
    boolean tooLate = minDate.isAfter(effectiveDate);
    return tooLate || tooSoon;
  }
}
