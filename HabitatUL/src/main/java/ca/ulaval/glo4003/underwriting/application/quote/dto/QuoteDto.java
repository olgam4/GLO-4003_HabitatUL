package ca.ulaval.glo4003.underwriting.application.quote.dto;

import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.shared.domain.Period;
import ca.ulaval.glo4003.underwriting.domain.premium.Premium;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteDto extends DataTransferObject {
  private QuoteId quoteId;
  private Premium premium;
  private DateTime expirationDate;
  private Period effectivePeriod;

  public QuoteDto(
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
