package ca.ulaval.glo4003.coverage.communication.policy;

import ca.ulaval.glo4003.coverage.application.policy.PolicyAppService;
import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.coverage.domain.policy.QuoteId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PolicyBoundedContextEventHandlerTest {
  private static final QuoteId QUOTE_ID = new QuoteId();

  @Mock private PolicyAppService policyAppService;

  private PolicyBoundedContextEventHandler subject;

  @Before
  public void setUp() {
    subject = new PolicyBoundedContextEventHandler(policyAppService);
  }

  @Test
  public void handlingPolicyCreationRequestedEvent_shouldDelegateToPolicyAppService() {
    PolicyCreationRequestedEvent event = new PolicyCreationRequestedEvent(QUOTE_ID);

    subject.handlePolicyCreationRequestedEvent(event);

    verify(policyAppService).issuePolicy(event);
  }
}
