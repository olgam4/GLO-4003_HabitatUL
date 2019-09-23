package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.Date;
import ca.ulaval.glo4003.shared.Premium;

import java.time.Duration;

public class QuoteFactory {
  private Duration quoteValidityPeriod;

  public QuoteFactory(Duration quoteValidityPeriod) {
    this.quoteValidityPeriod = quoteValidityPeriod;
  }

  public Quote create(Premium premium, QuoteRequest quoteRequest) {
    QuoteId quoteId = new QuoteId();
    Date expirationDate = Date.now().plus(quoteValidityPeriod);
    Date purchaseDate = Date.nullDate();
    return new Quote(quoteId, premium, quoteRequest, expirationDate, purchaseDate);
  }
}
