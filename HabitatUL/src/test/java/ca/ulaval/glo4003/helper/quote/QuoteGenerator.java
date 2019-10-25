package ca.ulaval.glo4003.helper.quote;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAssembler;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

public class QuoteGenerator {
  private static final java.time.Period COVERAGE_PERIOD = Period.ofMonths(12);

  private QuoteGenerator() {}

  public static QuoteDto createValidQuoteDto() {
    return new QuoteAssembler().from(createValidQuote());
  }

  public static Quote createValidQuote() {
    return createValidQuoteWithId(createQuoteId());
  }

  public static Quote createValidQuoteWithId(QuoteId quoteId) {
    Money price = MoneyGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createQuoteForm();
    DateTime expirationDate = createFutureDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(
        quoteId, quoteForm, COVERAGE_PERIOD, price, expirationDate, false, clockProvider);
  }

  public static Quote createValidQuoteWithEffectiveDateAndCoveragePeriod(
      Date effectiveDate, Period period) {
    QuoteId quoteId = createQuoteId();
    Money price = MoneyGenerator.create();
    QuoteForm quoteForm = QuoteFormBuilder.aQuoteForm().withEffectiveDate(effectiveDate).build();
    DateTime expirationDate = createFutureDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(quoteId, quoteForm, period, price, expirationDate, false, clockProvider);
  }

  public static Quote createExpiredQuote() {
    Money price = MoneyGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createQuoteForm();
    DateTime expirationDate = createPastDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(
        createQuoteId(), quoteForm, COVERAGE_PERIOD, price, expirationDate, false, clockProvider);
  }

  public static Quote createPurchasedQuote() {
    Money price = MoneyGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createQuoteForm();
    DateTime expirationDate = createPastDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(
        createQuoteId(), quoteForm, Period.ZERO, price, expirationDate, true, clockProvider);
  }

  private static QuoteId createQuoteId() {
    return new QuoteId();
  }

  private static DateTime createFutureDate() {
    Instant futureInstant = Faker.instance().date().future(365, TimeUnit.DAYS).toInstant();
    LocalDateTime futureDate = LocalDateTime.ofInstant(futureInstant, ZoneOffset.UTC);
    return DateTime.from(futureDate);
  }

  private static DateTime createPastDate() {
    Instant pastInstant = Faker.instance().date().past(365, TimeUnit.DAYS).toInstant();
    LocalDateTime pastDate = LocalDateTime.ofInstant(pastInstant, ZoneOffset.UTC);
    return DateTime.from(pastDate);
  }
}
