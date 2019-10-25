package ca.ulaval.glo4003.helper.quote;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.helper.TemporalGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAssembler;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import com.github.javafaker.Faker;

public class QuoteGenerator {
  private QuoteGenerator() {}

  public static QuoteDto createQuoteDto() {
    return new QuoteAssembler().from(createQuote());
  }

  public static Quote createQuote() {
    return new Quote(
        createQuoteId(),
        QuoteFormGenerator.createQuoteForm(),
        TemporalGenerator.createFutureDateTime(),
        TemporalGenerator.createPeriod(),
        MoneyGenerator.create(),
        Faker.instance().bool().bool(),
        TemporalGenerator.getClockProvider());
  }

  public static QuoteId createQuoteId() {
    return new QuoteId(Faker.instance().internet().uuid());
  }
}
