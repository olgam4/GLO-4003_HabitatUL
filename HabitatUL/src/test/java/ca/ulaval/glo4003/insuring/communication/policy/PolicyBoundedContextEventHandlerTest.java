package ca.ulaval.glo4003.insuring.communication.policy;

import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.insuring.helper.policy.PolicyGenerator.createPolicyPurchasedEvent;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PolicyBoundedContextEventHandlerTest {
  @Mock private PolicyAppService policyAppService;

  private PolicyBoundedContextEventHandler subject;

  @Before
  public void setUp() {
    subject = new PolicyBoundedContextEventHandler(policyAppService);
  }

  @Test
  public void handlingPolicyPurchasedEvent_shouldDelegateToPolicyAppService() {
    PolicyPurchasedEvent event = createPolicyPurchasedEvent();

    subject.handlePolicyPurchasedEvent(event);

    verify(policyAppService).issuePolicy(event);
  }
}
