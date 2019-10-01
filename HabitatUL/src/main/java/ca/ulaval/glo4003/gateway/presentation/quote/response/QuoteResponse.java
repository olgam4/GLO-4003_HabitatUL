package ca.ulaval.glo4003.gateway.presentation.quote.response;

import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteResponse {
  private QuoteId quoteId;
  private Premium premium;
  private DateTime expirationDate;

  public QuoteResponse(QuoteId quoteId, Premium premium, DateTime expirationDate) {
    this.quoteId = quoteId;
    this.premium = premium;
    this.expirationDate = expirationDate;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public Premium getPremium() {
    return premium;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }
}
