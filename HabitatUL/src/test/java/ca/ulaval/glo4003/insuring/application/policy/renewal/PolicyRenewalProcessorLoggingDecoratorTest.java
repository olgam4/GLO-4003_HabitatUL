package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.logging.Logger;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDateTime;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PolicyRenewalProcessorLoggingDecoratorTest {
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();
  private static final DateTime RENEWAL_EFFECTIVE_DATE = createDateTime();

  @Mock private PolicyRenewalProcessor policyRenewalProcessor;
  @Mock private Logger logger;

  private PolicyRenewalProcessorLoggingDecorator subject;

  @Before
  public void setUp() {
    subject = new PolicyRenewalProcessorLoggingDecorator(policyRenewalProcessor, logger);
  }

  @Test
  public void schedulingTask_shouldLogParamsAsInfo() {
    subject.scheduleRenewal(POLICY_ID, POLICY_RENEWAL_ID, RENEWAL_EFFECTIVE_DATE);

    verify(logger).info(contains(POLICY_RENEWAL_ID.toRepresentation()));
  }

  @Test
  public void schedulingTask_shouldDelegateToTaskScheduler() {
    subject.scheduleRenewal(POLICY_ID, POLICY_RENEWAL_ID, RENEWAL_EFFECTIVE_DATE);

    verify(policyRenewalProcessor)
        .scheduleRenewal(POLICY_ID, POLICY_RENEWAL_ID, RENEWAL_EFFECTIVE_DATE);
  }

  @Test
  public void cancelingTask_shouldLogParamsAsInfo() {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(logger).info(contains(POLICY_RENEWAL_ID.toRepresentation()));
  }

  @Test
  public void cancelingTask_shouldDelegateToTaskScheduler() {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyRenewalProcessor).cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }
}
