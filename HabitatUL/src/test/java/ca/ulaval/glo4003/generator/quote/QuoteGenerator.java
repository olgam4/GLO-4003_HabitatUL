package ca.ulaval.glo4003.generator.quote;

import ca.ulaval.glo4003.generator.price.PriceGenerator;
import ca.ulaval.glo4003.generator.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAssembler;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.price.Price;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

public class QuoteGenerator {
  private QuoteGenerator() {}

  public static QuoteDto createValidQuoteDto() {
    return new QuoteAssembler().from(createValidQuote());
  }

  public static Quote createValidQuote() {
    return createValidQuoteWithId(createQuoteId());
  }

  public static Quote createValidQuoteWithId(QuoteId quoteId) {
    Price price = PriceGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createQuoteForm();
    DateTime expirationDate = createFutureDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(quoteId, price, quoteForm, expirationDate, false, clockProvider);
  }

  public static Quote createValidQuoteWithEffectiveDate(Date effectiveDate) {
    QuoteId quoteId = createQuoteId();
    Price price = PriceGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createQuoteFormWithEffectiveDate(effectiveDate);
    DateTime expirationDate = createFutureDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(quoteId, price, quoteForm, expirationDate, false, clockProvider);
  }

  public static Quote createExpiredQuote() {
    Price price = PriceGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createQuoteForm();
    DateTime expirationDate = createPastDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(createQuoteId(), price, quoteForm, expirationDate, false, clockProvider);
  }

  public static Quote createPurchasedQuote() {
    Price price = PriceGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createQuoteForm();
    DateTime expirationDate = createPastDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(createQuoteId(), price, quoteForm, expirationDate, true, clockProvider);
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
