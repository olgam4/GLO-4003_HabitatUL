package ca.ulaval.glo4003.underwriting.application.quote.dto;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteDto {
  private QuoteId quoteId;

  public QuoteDto(QuoteId quoteId) {
    this.quoteId = quoteId;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }
}
