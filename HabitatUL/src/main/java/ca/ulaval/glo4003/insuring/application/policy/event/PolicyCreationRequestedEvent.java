package ca.ulaval.glo4003.insuring.application.policy.event;

import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

public class PolicyCreationRequestedEvent extends Event {
  private final String quoteKey;
  private final Period coveragePeriod;
  private final Date purchaseDate;
  private final Amount coverageAmount;

  public PolicyCreationRequestedEvent(
      String quoteKey, Period coveragePeriod, Date purchaseDate, Amount coverageAmount) {
    this.quoteKey = quoteKey;
    this.coveragePeriod = coveragePeriod;
    this.purchaseDate = purchaseDate;
    this.coverageAmount = coverageAmount;
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

  public Amount getCoverageAmount() {
    return coverageAmount;
  }
}