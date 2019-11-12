package ca.ulaval.glo4003.helper.quote;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.quote.QuoteGenerator.createQuoteId;

public class QuoteBuilder {
  private static final QuoteId DEFAULT_QUOTE_ID = createQuoteId();
  private static final QuoteForm DEFAULT_QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final DateTime DEFAULT_EXPIRATION_DATE = TemporalGenerator.createFutureDateTime();
  private static final Period DEFAULT_EFFECTIVE_PERIOD = TemporalGenerator.createPeriod();
  private static final CoverageDetails DEFAULT_COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails DEFAULT_PREMIUM_DETAILS = createPremiumDetails();
  private static final Boolean DEFAULT_PURCHASED = Faker.instance().bool().bool();
  private static final ClockProvider DEFAULT_CLOCK_PROVIDER = TemporalGenerator.getClockProvider();

  private QuoteId quoteId = DEFAULT_QUOTE_ID;
  private QuoteForm quoteForm = DEFAULT_QUOTE_FORM;
  private DateTime expirationDate = DEFAULT_EXPIRATION_DATE;
  private Period effectivePeriod = DEFAULT_EFFECTIVE_PERIOD;
  private CoverageDetails coverageDetails = DEFAULT_COVERAGE_DETAILS;
  private PremiumDetails premiumDetails = DEFAULT_PREMIUM_DETAILS;
  private Boolean purchased = DEFAULT_PURCHASED;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private QuoteBuilder() {}

  public static QuoteBuilder aQuote() {
    return new QuoteBuilder();
  }

  public QuoteBuilder withId(QuoteId id) {
    this.quoteId = id;
    return this;
  }

  public QuoteBuilder expired() {
    this.expirationDate = TemporalGenerator.createPastDateTime();
    return this;
  }

  public QuoteBuilder unPurchased() {
    this.purchased = false;
    return this;
  }

  public QuoteBuilder purchased() {
    this.purchased = true;
    return this;
  }

  public Quote build() {
    return new Quote(
        quoteId,
        quoteForm,
        expirationDate,
        effectivePeriod,
        coverageDetails,
        premiumDetails,
        purchased,
        clockProvider);
  }
}
