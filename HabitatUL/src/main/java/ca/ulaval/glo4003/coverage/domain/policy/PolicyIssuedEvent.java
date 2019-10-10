package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.mediator.event.Event;
import ca.ulaval.glo4003.mediator.event.EventChannel;
import ca.ulaval.glo4003.mediator.event.EventPayload;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;

public class PolicyIssuedEvent extends Event {
  private static final String POLICY_ISSUED_EVENT_TYPE = "policyIssuedEvent";
  private static final String POLICY_ID_PAYLOAD_KEY = "policyId";
  private static final String QUOTE_ID_PAYLOAD_KEY = "quoteId";

  public PolicyIssuedEvent(PolicyId policyId, QuoteId quoteId, ClockProvider clockProvider) {
    super(
        EventChannel.POLICIES,
        POLICY_ISSUED_EVENT_TYPE,
        createPayload(policyId, quoteId),
        clockProvider);
  }

  private static EventPayload createPayload(PolicyId policyId, QuoteId quoteId) {
    return EventPayload.EventPayloadBuilder.anEventPayload()
        .withEntry(POLICY_ID_PAYLOAD_KEY, policyId.getValue().toString())
        .withEntry(QUOTE_ID_PAYLOAD_KEY, quoteId.getValue().toString())
        .build();
  }
}
