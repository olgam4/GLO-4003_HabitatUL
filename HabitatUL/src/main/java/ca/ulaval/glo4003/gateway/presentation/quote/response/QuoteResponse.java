package ca.ulaval.glo4003.gateway.presentation.quote.response;

import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.shared.domain.Period;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"quoteId", "premium", "effectivePeriod", "expirationDate"})
public class QuoteResponse {
  private QuoteId quoteId;
  private Premium premium;
  private Period effectivePeriod;
  private DateTime expirationDate;

  public QuoteResponse(
      QuoteId quoteId, Premium premium, Period effectivePeriod, DateTime expirationDate) {
    this.quoteId = quoteId;
    this.premium = premium;
    this.effectivePeriod = effectivePeriod;
    this.expirationDate = expirationDate;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public Premium getPremium() {
    return premium;
  }

  public Period getEffectivePeriod() {
    return effectivePeriod;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }
}
