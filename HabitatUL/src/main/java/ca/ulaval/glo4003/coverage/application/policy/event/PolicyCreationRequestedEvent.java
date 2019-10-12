package ca.ulaval.glo4003.coverage.application.policy.event;

import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;
import ca.ulaval.glo4003.mediator.Event;

public class PolicyCreationRequestedEvent extends Event {
  @Deprecated private QuoteId quoteId;

  public PolicyCreationRequestedEvent(QuoteId quoteId) {
    this.quoteId = quoteId;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }
}
