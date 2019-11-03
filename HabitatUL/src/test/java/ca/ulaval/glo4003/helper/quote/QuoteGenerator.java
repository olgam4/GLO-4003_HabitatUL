package ca.ulaval.glo4003.helper.quote;

import ca.ulaval.glo4003.underwriting.application.quote.QuoteAssembler;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.domain.quote.Quote;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

import static ca.ulaval.glo4003.helper.MoneyGenerator.createMoney;
import static ca.ulaval.glo4003.helper.TemporalGenerator.*;
import static ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator.createQuoteForm;

public class QuoteGenerator {
  private QuoteGenerator() {}

  public static QuoteDto createQuoteDto() {
    return new QuoteAssembler().from(createQuote());
  }

  public static Quote createQuote() {
    return new Quote(
        createQuoteId(),
        createQuoteForm(),
        createFutureDateTime(),
        createPeriod(),
        createMoney(),
        false,
        getClockProvider());
  }

  public static QuoteId createQuoteId() {
    return new QuoteId();
  }
}
