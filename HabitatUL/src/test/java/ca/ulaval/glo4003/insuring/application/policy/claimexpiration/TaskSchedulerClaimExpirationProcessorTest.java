package ca.ulaval.glo4003.insuring.application.policy.claimexpiration;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.shared.application.threading.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.insuring.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.createDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TaskSchedulerClaimExpirationProcessorTest {
  private static final ClaimId TASK_KEY = createClaimId();
  private static final DateTime CLAIM_EXPIRATION_DATE = createDateTime();

  @Mock private TaskScheduler taskScheduler;
  @Mock private ClaimRepository claimRepository;

  private TaskSchedulerClaimExpirationProcessor subject;

  @Before
  public void setUp() {
    subject = new TaskSchedulerClaimExpirationProcessor(taskScheduler, claimRepository);
  }

  @Test
  public void schedulingExpiration_shouldScheduleExpirationTask() {
    subject.scheduleExpiration(TASK_KEY, CLAIM_EXPIRATION_DATE);

    verify(taskScheduler)
        .schedule(eq(TASK_KEY), any(ClaimExpirationTask.class), eq(CLAIM_EXPIRATION_DATE));
  }

  @Test
  public void cancelingExpiration_shouldCancelTask() {
    subject.cancelExpiration(TASK_KEY);

    verify(taskScheduler).cancel(TASK_KEY);
  }
}
