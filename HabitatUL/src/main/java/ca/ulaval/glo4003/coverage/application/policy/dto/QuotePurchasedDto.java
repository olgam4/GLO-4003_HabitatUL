package ca.ulaval.glo4003.coverage.application.policy.dto;

import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;
import ca.ulaval.glo4003.shared.application.DataTransferObject;

public class QuotePurchasedDto extends DataTransferObject {
  private QuoteId quoteId;

  public QuotePurchasedDto(QuoteId quoteId) {
    this.quoteId = quoteId;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }
}
