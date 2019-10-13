package ca.ulaval.glo4003.coverage.application.policy.event;

import ca.ulaval.glo4003.mediator.Event;

public class PolicyCreationRequestedEvent extends Event {
  private String quoteKey;

  public PolicyCreationRequestedEvent(String quoteKey) {
    this.quoteKey = quoteKey;
  }

  public String getQuoteKey() {
    return quoteKey;
  }
}
