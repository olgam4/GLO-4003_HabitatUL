package ca.ulaval.glo4003.management.presentation.user;

import ca.ulaval.glo4003.builder.EventBuilder;
import ca.ulaval.glo4003.management.application.user.UserAppService;
import ca.ulaval.glo4003.mediator.event.Event;
import ca.ulaval.glo4003.mediator.event.EventChannel;
import ca.ulaval.glo4003.mediator.event.EventPayload;
import ca.ulaval.glo4003.shared.domain.Money;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static ca.ulaval.glo4003.management.presentation.user.UserBoundedContext.POLICY_ISSUED_EVENT_TYPE;
import static ca.ulaval.glo4003.management.presentation.user.UserBoundedContext.QUOTE_PURCHASED_EVENT_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserBoundedContextTest {
  @Mock private UserAppService userAppService;

  private UserBoundedContext subject;

  @Before
  public void setUp() {
    subject = new UserBoundedContext(userAppService);
  }

  @Test
  public void processingEvent_withQuotePurchasedEvent_shouldDelegateToUserAppService() {
    String quoteId = Faker.instance().internet().uuid();
    BigDecimal price = BigDecimal.valueOf(Faker.instance().number().randomDouble(0, 0, 1000));
    EventPayload payload =
        EventPayload.EventPayloadBuilder.anEventPayload()
            .withEntry("quoteId", quoteId)
            .withEntry("price", price.toString())
            .build();
    Event event =
        EventBuilder.anEvent()
            .withEventChannel(EventChannel.QUOTES)
            .withEventType(QUOTE_PURCHASED_EVENT_TYPE)
            .withPayload(payload)
            .build();

    subject.process(event);

    verify(userAppService).processQuotePayment(quoteId, new Money(price));
  }

  @Test
  public void processingEvent_withPolicyIssuedEvent_shouldDelegateToUserAppService() {
    String policyId = Faker.instance().internet().uuid();
    String quoteId = Faker.instance().internet().uuid();
    EventPayload payload =
        EventPayload.EventPayloadBuilder.anEventPayload()
            .withEntry("policyId", policyId)
            .withEntry("quoteId", quoteId)
            .build();
    Event event =
        EventBuilder.anEvent()
            .withEventChannel(EventChannel.POLICIES)
            .withEventType(POLICY_ISSUED_EVENT_TYPE)
            .withPayload(payload)
            .build();

    subject.process(event);

    verify(userAppService).associatePolicy(quoteId, policyId);
  }

  @Test
  public void processingEvent_withUnregisteredEvent_shouldNotBeProcessed() {
    Event event =
        EventBuilder.anEvent()
            .withEventChannel(EventChannel.POLICIES)
            .withEventType("AN UNREGISTERED EVENT TYPE")
            .build();

    subject.process(event);

    verify(userAppService, never()).associatePolicy(any(), any());
  }
}
