package ca.ulaval.glo4003.generator.quote;

import ca.ulaval.glo4003.generator.premium.PremiumGenerator;
import ca.ulaval.glo4003.shared.domain.ClockProvider;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAssembler;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
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
    return QuoteAssembler.from(createValidQuote());
  }

  public static Quote createValidQuote() {
    return createValidQuoteWithId(createQuoteId());
  }

  public static Quote createValidQuoteWithId(QuoteId quoteId) {
    Premium premium = PremiumGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createValidQuoteForm();
    Date expirationDate = createFutureDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(quoteId, premium, quoteForm, expirationDate, false, clockProvider);
  }

  public static Quote createExpiredQuote() {
    Premium premium = PremiumGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createValidQuoteForm();
    Date expirationDate = createPastDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(createQuoteId(), premium, quoteForm, expirationDate, false, clockProvider);
  }

  public static Quote createPurchasedQuote() {
    Premium premium = PremiumGenerator.create();
    QuoteForm quoteForm = QuoteFormGenerator.createValidQuoteForm();
    Date expirationDate = createPastDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(createQuoteId(), premium, quoteForm, expirationDate, true, clockProvider);
  }

  private static QuoteId createQuoteId() {
    return new QuoteId();
  }

  private static Date createFutureDate() {
    Instant futureInstant = Faker.instance().date().future(365, TimeUnit.DAYS).toInstant();
    LocalDateTime futureDate = LocalDateTime.ofInstant(futureInstant, ZoneOffset.UTC);
    return Date.from(futureDate);
  }

  private static Date createPastDate() {
    Instant pastInstant = Faker.instance().date().past(365, TimeUnit.DAYS).toInstant();
    LocalDateTime pastDate = LocalDateTime.ofInstant(pastInstant, ZoneOffset.UTC);
    return Date.from(pastDate);
  }
}
