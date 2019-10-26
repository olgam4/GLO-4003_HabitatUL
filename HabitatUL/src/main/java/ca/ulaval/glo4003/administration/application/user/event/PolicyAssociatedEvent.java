package ca.ulaval.glo4003.administration.application.user.event;

import ca.ulaval.glo4003.mediator.Event;

public class PolicyAssociatedEvent extends Event {
  private final String policyKey;
  private final String quoteKey;

  public PolicyAssociatedEvent(String policyKey, String quoteKey) {
    this.policyKey = policyKey;
    this.quoteKey = quoteKey;
  }

  public String getPolicyKey() {
    return policyKey;
  }

  public String getQuoteKey() {
    return quoteKey;
  }
}
