package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.shared.ClockProvider;
import ca.ulaval.glo4003.shared.Date;
import ca.ulaval.glo4003.shared.Premium;

import java.time.Duration;

public class QuoteFactory {
  private Duration quoteValidityPeriod;
  private ClockProvider clockProvider;

  public QuoteFactory(Duration quoteValidityPeriod, ClockProvider clockProvider) {
    this.quoteValidityPeriod = quoteValidityPeriod;
    this.clockProvider = clockProvider;
  }

  public Quote create(Premium premium, QuoteRequest quoteRequest) {
    QuoteId quoteId = new QuoteId();
    Date expirationDate = Date.now(clockProvider.getClock()).plus(quoteValidityPeriod);
    Date purchaseDate = Date.nullDate();
    return new Quote(quoteId, premium, quoteRequest, expirationDate, purchaseDate, clockProvider);
  }
}
