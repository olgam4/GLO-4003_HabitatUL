package ca.ulaval.glo4003.administration.communication.user;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.administration.application.user.event.PolicyAssociatedEvent;
import ca.ulaval.glo4003.administration.application.user.event.QuotePurchaseConfirmedEvent;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
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
  private static final String POLICY_KEY = Faker.instance().internet().uuid();
  private static final String QUOTE_KEY = Faker.instance().internet().uuid();
  private static final Money TOTAL = MoneyGenerator.createMoney();

  @Mock private UserAppService userAppService;

  private UserBoundedContextEventHandler subject;

  @Before
  public void setUp() {
    subject = new UserBoundedContextEventHandler(userAppService);
  }

  @Test
  public void handlingQuotePaymentRequestedEvent_shouldDelegateToUserAppService() {
    QuotePurchaseConfirmedEvent event = new QuotePurchaseConfirmedEvent(QUOTE_KEY, TOTAL);

    subject.handleQuotePurchaseConfirmedEvent(event);

    verify(userAppService).processQuotePayment(QUOTE_KEY, TOTAL);
  }

  @Test
  public void handlingPolicyAssociatedEvent_shouldDelegateToUserAppService() {
    PolicyAssociatedEvent event = new PolicyAssociatedEvent(POLICY_KEY, QUOTE_KEY);

    subject.handlePolicyAssociatedEvent(event);

    verify(userAppService).associatePolicy(QUOTE_KEY, POLICY_KEY);
  }
}
