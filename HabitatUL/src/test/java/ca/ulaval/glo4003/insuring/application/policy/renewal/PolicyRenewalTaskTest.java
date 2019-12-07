package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PolicyRenewalTaskTest {
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();

  @Mock private PolicyRepository policyRepository;
  @Mock private Policy policy;
  @Mock private Logger logger;

  private PolicyRenewalTask subject;

  @Before
  public void setUp() throws PolicyNotFoundException {
    when(policyRepository.getById(any(PolicyId.class))).thenReturn(policy);
    subject = new PolicyRenewalTask(POLICY_ID, POLICY_RENEWAL_ID, policyRepository, logger);
  }

  @Test
  public void runningTask_shouldGetPolicyById() throws PolicyNotFoundException {
    subject.run();

    verify(policyRepository).getById(POLICY_ID);
  }

  @Test
  public void runningTask_shouldConfirmRenewal() {
    subject.run();

    verify(policy).confirmRenewal(POLICY_RENEWAL_ID);
  }

  @Test
  public void runningTask_shouldUpdatePolicy() throws PolicyNotFoundException {
    subject.run();

    verify(policyRepository).update(policy);
  }

  public void runningTask_withNotExistingPolicy_shouldLogErrorAsSevere()
      throws PolicyNotFoundException {
    when(policyRepository.getById(POLICY_ID)).thenThrow(PolicyNotFoundException.class);

    subject.run();

    verify(logger).severe(anyString());
  }
}
