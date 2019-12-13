package ca.ulaval.glo4003.helper.quote;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteStatus;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.quote.QuoteGenerator.createQuoteId;
import static ca.ulaval.glo4003.helper.quote.QuoteGenerator.createQuoteStatus;

public class QuoteBuilder {
  private final QuoteId DEFAULT_QUOTE_ID = createQuoteId();
  private final QuoteStatus DEFAULT_STATUS = createQuoteStatus();
  private final QuoteForm DEFAULT_QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private final DateTime DEFAULT_EXPIRATION_DATE = TemporalGenerator.createFutureDateTime();
  private final Period DEFAULT_EFFECTIVE_PERIOD = TemporalGenerator.createPeriod();
  private final CoverageDetails DEFAULT_COVERAGE_DETAILS = createCoverageDetails();
  private final PremiumDetails DEFAULT_PREMIUM_DETAILS = createPremiumDetails();
  private final ClockProvider DEFAULT_CLOCK_PROVIDER = TemporalGenerator.getClockProvider();

  private QuoteId quoteId = DEFAULT_QUOTE_ID;
  private QuoteStatus status = DEFAULT_STATUS;
  private QuoteForm quoteForm = DEFAULT_QUOTE_FORM;
  private DateTime expirationDate = DEFAULT_EXPIRATION_DATE;
  private Period effectivePeriod = DEFAULT_EFFECTIVE_PERIOD;
  private CoverageDetails coverageDetails = DEFAULT_COVERAGE_DETAILS;
  private PremiumDetails premiumDetails = DEFAULT_PREMIUM_DETAILS;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private QuoteBuilder() {}

  public static QuoteBuilder aQuote() {
    return new QuoteBuilder();
  }

  public QuoteBuilder withId(QuoteId id) {
    this.quoteId = id;
    return this;
  }

  public QuoteBuilder withStatus(QuoteStatus status) {
    this.status = status;
    return this;
  }

  public Quote build() {
    return new Quote(
        quoteId,
        status,
        quoteForm,
        expirationDate,
        effectivePeriod,
        coverageDetails,
        premiumDetails,
        clockProvider);
  }
}
