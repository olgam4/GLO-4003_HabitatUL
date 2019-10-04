package ca.ulaval.glo4003.coverage.presentation.policy;

import ca.ulaval.glo4003.builder.EventBuilder;
import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;
import ca.ulaval.glo4003.mediator.event.Event;
import ca.ulaval.glo4003.mediator.event.EventChannel;
import ca.ulaval.glo4003.mediator.event.EventPayload;
import ca.ulaval.glo4003.mediator.event.EventPayload.EventPayloadBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.coverage.presentation.policy.PolicyBoundedContext.QUOTE_PURCHASED_EVENT_TYPE;
import static ca.ulaval.glo4003.matcher.policy.QuotePurchasedDtoMatcher.mockitoQuotePurchasedDtoMatcher;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PolicyBoundedContextTest {
  @Mock private PolicyAppService policyAppService;

  private PolicyBoundedContext subject;

  @Before
  public void setUp() {
    subject = new PolicyBoundedContext(policyAppService);
  }

  @Test
  public void processingEvent_withQuotePurchasedEvent_shouldDelegateToPolicyAppService() {
    // TODO: add generator
    EventPayload payload =
        EventPayloadBuilder.anEventPayload()
            .withEntry("quoteId", new QuoteId().getValue().toString())
            .build();
    Event event =
        EventBuilder.anEvent()
            .withEventChannel(EventChannel.QUOTES)
            .withEventType(QUOTE_PURCHASED_EVENT_TYPE)
            .withPayload(payload)
            .build();

    subject.process(event);

    verify(policyAppService).issuePolicy(mockitoQuotePurchasedDtoMatcher(event));
  }

  @Test
  public void processingEvent_withUnregisteredEvent_shouldNotBeProcessed() {
    Event event =
        EventBuilder.anEvent()
            .withEventChannel(EventChannel.QUOTES)
            .withEventType("AN UNREGISTERED EVENT TYPE")
            .build();

    subject.process(event);

    verify(policyAppService, never()).issuePolicy(any());
  }
}
