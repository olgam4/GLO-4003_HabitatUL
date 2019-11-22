package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PolicyRenewalTaskTest {
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();

  @Mock private PolicyAppService policyAppService;

  private PolicyRenewalTask subject;

  @Before
  public void setUp() {
    subject = new PolicyRenewalTask(policyAppService, POLICY_ID, POLICY_RENEWAL_ID);
  }

  @Test
  public void runningTask_shouldDelegateToPolicyAppService() {
    subject.run();

    verify(policyAppService).confirmRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }
}
