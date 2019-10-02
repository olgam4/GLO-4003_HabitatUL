package ca.ulaval.glo4003.coverage.presentation.policy;

import ca.ulaval.glo4003.builder.EventBuilder;
import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.mediator.EventChannel;
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
    Event event =
        EventBuilder.anEvent()
            .withEventChannel(EventChannel.QUOTES)
            .withEventType(QUOTE_PURCHASED_EVENT_TYPE)
            .build();

    subject.process(event);

    verify(policyAppService).createPolicy(mockitoQuotePurchasedDtoMatcher(event));
  }

  @Test
  public void processingEvent_withUnregisteredEvent_shouldNotBeProcessed() {
    Event event =
        EventBuilder.anEvent()
            .withEventChannel(EventChannel.QUOTES)
            .withEventType("AN UNREGISTERED EVENT TYPE")
            .build();

    subject.process(event);

    verify(policyAppService, never()).createPolicy(any());
  }
}
