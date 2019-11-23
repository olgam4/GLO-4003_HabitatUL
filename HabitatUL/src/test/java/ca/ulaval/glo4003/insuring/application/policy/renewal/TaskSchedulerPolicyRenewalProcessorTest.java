package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.threading.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDateTime;
import static ca.ulaval.glo4003.insuring.application.policy.renewal.TaskSchedulerPolicyRenewalProcessor.RenewalTaskKey;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TaskSchedulerPolicyRenewalProcessorTest {
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();
  private static final RenewalTaskKey TASK_KEY = new RenewalTaskKey(POLICY_ID, POLICY_RENEWAL_ID);
  private static final DateTime RENEWAL_EFFECTIVE_DATE = createDateTime();

  @Mock private TaskScheduler taskScheduler;
  @Mock private PolicyRepository policyRepository;

  private TaskSchedulerPolicyRenewalProcessor subject;

  @Before
  public void setUp() {
    subject = new TaskSchedulerPolicyRenewalProcessor(taskScheduler, policyRepository);
  }

  @Test
  public void schedulingRenewal_shouldScheduleRenewalTask() {
    subject.scheduleRenewal(POLICY_ID, POLICY_RENEWAL_ID, RENEWAL_EFFECTIVE_DATE);

    verify(taskScheduler)
        .schedule(eq(TASK_KEY), any(PolicyRenewalTask.class), eq(RENEWAL_EFFECTIVE_DATE));
  }

  @Test
  public void cancellingRenewal_shouldCancelTask() {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(taskScheduler).cancel(TASK_KEY);
  }
}
