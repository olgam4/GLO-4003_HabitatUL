package ca.ulaval.glo4003.coverage.application.policy.event;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

public class PolicyCreationRequestedEvent extends Event {
  private String quoteKey;
  private Period coveragePeriod;
  private Date purchaseDate;

  public PolicyCreationRequestedEvent(String quoteKey, Period coveragePeriod, Date purchaseDate) {
    this.quoteKey = quoteKey;
    this.coveragePeriod = coveragePeriod;
    this.purchaseDate = purchaseDate;
  }

  public String getQuoteKey() {
    return quoteKey;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public Date getPurchaseDate() {
    return purchaseDate;
  }
}
