package ca.ulaval.glo4003.administration.communication.user;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.application.user.event.PolicyAssociatedEvent;
import ca.ulaval.glo4003.administration.application.user.event.QuotePaymentRequestedEvent;
import ca.ulaval.glo4003.generator.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserBoundedContextEventHandlerTest {
  private static final String POLICY_KEY = Faker.instance().medical().hospitalName();
  private static final String QUOTE_KEY = Faker.instance().medical().diseaseName();
  private static final Money TOTAL = MoneyGenerator.create();

  @Mock private UserAppService userAppService;

  private UserBoundedContextEventHandler subject;

  @Before
  public void setUp() {
    subject = new UserBoundedContextEventHandler(userAppService);
  }

  @Test
  public void handlingQuotePaymentRequestedEvent_shouldDelegateToUserAppService() {
    QuotePaymentRequestedEvent event = new QuotePaymentRequestedEvent(QUOTE_KEY, TOTAL);

    subject.handleQuotePaymentRequestedEvent(event);

    verify(userAppService).processQuotePayment(QUOTE_KEY, TOTAL);
  }

  @Test
  public void handlingPolicyAssociatedEvent_shouldDelegateToUserAppService() {
    PolicyAssociatedEvent event = new PolicyAssociatedEvent(POLICY_KEY, QUOTE_KEY);

    subject.handlePolicyAssociatedEvent(event);

    verify(userAppService).associatePolicy(QUOTE_KEY, POLICY_KEY);
  }
}
