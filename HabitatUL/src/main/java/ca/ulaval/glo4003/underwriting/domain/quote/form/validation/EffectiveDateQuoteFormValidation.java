package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.error.InvalidEffectiveDateError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;

import java.time.Period;

public class EffectiveDateQuoteFormValidation implements QuoteFormValidation {
  static final int NUMBER_OF_MONTHS_OF_COVERAGE = 12;
  private ClockProvider clockProvider;

  public EffectiveDateQuoteFormValidation(ClockProvider clockProvider) {
    this.clockProvider = clockProvider;
  }

  @Override
  public void validate(QuoteForm quoteForm) {
    if (isInvalidEffectiveDate(quoteForm.getEffectiveDate())) {
      throw new InvalidEffectiveDateError();
    }
  }

  private boolean isInvalidEffectiveDate(Date effectiveDate) {
    Date minDate = Date.now(clockProvider.getClock());
    Date maxDate = minDate.plus(Period.ofMonths(NUMBER_OF_MONTHS_OF_COVERAGE));
    boolean tooSoon = maxDate.isBefore(effectiveDate);
    boolean tooLate = minDate.isAfter(effectiveDate);
    return tooLate || tooSoon;
  }
}