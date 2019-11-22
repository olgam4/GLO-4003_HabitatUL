package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDateTime;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TaskSchedulerPolicyRenewalProcessorTest {
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();
  private static final DateTime RENEWAL_EFFECTIVE_DATE = createDateTime();
  private static final Map.Entry<PolicyId, PolicyRenewalId> KEY =
      new AbstractMap.SimpleEntry<>(POLICY_ID, POLICY_RENEWAL_ID);
  private static final String TASK_ID = Faker.instance().internet().uuid();

  @Mock private TaskScheduler taskScheduler;
  @Mock private PolicyAppService policyAppService;

  private TaskSchedulerPolicyRenewalProcessor subject;
  private Map<Map.Entry<PolicyId, PolicyRenewalId>, String> scheduledRenewals;

  @Before
  public void setUp() {
    scheduledRenewals = new HashMap<>();
    subject = new TaskSchedulerPolicyRenewalProcessor(taskScheduler, scheduledRenewals);
  }

  @Test
  public void schedulingRenewal_shouldScheduleRenewalTask() {
    subject.scheduleRenewal(policyAppService, POLICY_ID, POLICY_RENEWAL_ID, RENEWAL_EFFECTIVE_DATE);

    verify(taskScheduler).schedule(any(PolicyRenewalTask.class), eq(RENEWAL_EFFECTIVE_DATE));
  }

  @Test
  public void schedulingRenewal_shouldRememberTaskId() {
    subject.scheduleRenewal(policyAppService, POLICY_ID, POLICY_RENEWAL_ID, RENEWAL_EFFECTIVE_DATE);

    assertTrue(!scheduledRenewals.isEmpty());
  }

  @Test
  public void cancellingRenewal_shouldCancelTask() {
    HashMap<Map.Entry<PolicyId, PolicyRenewalId>, String> scheduledRenewals =
        new HashMap<Map.Entry<PolicyId, PolicyRenewalId>, String>() {
          {
            put(KEY, TASK_ID);
          }
        };
    subject = new TaskSchedulerPolicyRenewalProcessor(taskScheduler, scheduledRenewals);

    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(taskScheduler).cancel(TASK_ID);
  }

  @Test
  public void cancellingRenewal_withNotScheduledRenewal_shouldNotThrow() {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }

  @Test
  public void completingRenewal_shouldForgetTask() {
    HashMap<Map.Entry<PolicyId, PolicyRenewalId>, String> scheduledRenewals =
        new HashMap<Map.Entry<PolicyId, PolicyRenewalId>, String>() {
          {
            put(KEY, TASK_ID);
          }
        };
    subject = new TaskSchedulerPolicyRenewalProcessor(taskScheduler, scheduledRenewals);

    subject.completeRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    assertTrue(scheduledRenewals.isEmpty());
  }

  @Test
  public void completingRenewal_withNotScheduledRenewal_shouldNotThrow() {
    subject.completeRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }
}
