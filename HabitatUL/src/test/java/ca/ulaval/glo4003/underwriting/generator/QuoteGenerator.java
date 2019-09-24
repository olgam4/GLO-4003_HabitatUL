package ca.ulaval.glo4003.underwriting.generator;

import ca.ulaval.glo4003.shared.ClockProvider;
import ca.ulaval.glo4003.shared.Date;
import ca.ulaval.glo4003.shared.FixedClockProvider;
import ca.ulaval.glo4003.shared.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteRequest;
import com.github.javafaker.Faker;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;

public class QuoteGenerator {
  private QuoteGenerator() {}

  public static Quote createValidQuote() {
    Premium premium = new Premium();
    QuoteRequest quoteRequest = QuoteRequestGenerator.createValidQuoteRequest();
    Date expirationDate = createFutureDate();
    Date purchaseDate = Date.nullDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(
        new QuoteId(), premium, quoteRequest, expirationDate, purchaseDate, clockProvider);
  }

  public static Quote createExpiredQuote() {
    Premium premium = new Premium();
    QuoteRequest quoteRequest = QuoteRequestGenerator.createValidQuoteRequest();
    Date expirationDate = createPastDate();
    Date purchaseDate = Date.nullDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(
        new QuoteId(), premium, quoteRequest, expirationDate, purchaseDate, clockProvider);
  }

  public static Quote createPurchasedQuote() {
    Premium premium = new Premium();
    QuoteRequest quoteRequest = QuoteRequestGenerator.createValidQuoteRequest();
    Date expirationDate = createPastDate();
    Date purchaseDate = createPastDate();
    ClockProvider clockProvider = new FixedClockProvider();
    return new Quote(
        new QuoteId(), premium, quoteRequest, expirationDate, purchaseDate, clockProvider);
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
