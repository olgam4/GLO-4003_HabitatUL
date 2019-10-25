package ca.ulaval.glo4003.helper.quote;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.TemporalGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.quote.QuoteGenerator.createQuoteId;

public class QuoteBuilder {
  private static final QuoteId DEFAULT_QUOTE_ID = createQuoteId();
  private static final QuoteForm DEFAULT_QUOTE_FORM = QuoteFormGenerator.createQuoteForm();
  private static final DateTime DEFAULT_EXPIRATION_DATE = TemporalGenerator.createFutureDateTime();
  private static final Period DEFAULT_EFFECTIVE_PERIOD = TemporalGenerator.createPeriod();
  private static final Money DEFAULT_PRICE = MoneyGenerator.create();
  private static final Boolean DEFAULT_PURCHASED = Faker.instance().bool().bool();
  private static final ClockProvider DEFAULT_CLOCK_PROVIDER = TemporalGenerator.getClockProvider();

  private QuoteId quoteId = DEFAULT_QUOTE_ID;
  private QuoteForm quoteForm = DEFAULT_QUOTE_FORM;
  private DateTime expirationDate = DEFAULT_EXPIRATION_DATE;
  private Period effectivePeriod = DEFAULT_EFFECTIVE_PERIOD;
  private Money price = DEFAULT_PRICE;
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
        quoteId, quoteForm, expirationDate, effectivePeriod, price, purchased, clockProvider);
  }
}
