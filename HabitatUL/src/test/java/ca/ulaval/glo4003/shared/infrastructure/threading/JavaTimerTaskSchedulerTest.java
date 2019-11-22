package ca.ulaval.glo4003.shared.infrastructure.threading;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPastDateTime;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;
import static java.time.ZoneOffset.UTC;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class JavaTimerTaskSchedulerTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final DateTime SCHEDULED_PROCESSING_DATE_TIME =
      DateTime.now(CLOCK_PROVIDER.getClock()).plus(Duration.ofMinutes(1));
  private static final String TASK_ID = Faker.instance().internet().uuid();
  private static final String ANOTHER_TASK_ID = Faker.instance().internet().uuid();

  @Mock private Timer timer;
  @Mock private Runnable runnable;
  @Mock private TimerTask timerTask;

  private JavaTimerTaskScheduler subject;
  private Map<String, TimerTask> tasks;

  @Before
  public void setUp() {
    tasks = new HashMap<>();
    subject = new JavaTimerTaskScheduler(timer, tasks, CLOCK_PROVIDER);
  }

  @Test
  public void schedulingTask_shouldComputeDelay() {
    subject.schedule(runnable, SCHEDULED_PROCESSING_DATE_TIME);

    long expectedDelay =
        SCHEDULED_PROCESSING_DATE_TIME.getValue().toInstant(UTC).toEpochMilli()
            - DateTime.now(CLOCK_PROVIDER.getClock()).getValue().toInstant(UTC).toEpochMilli();
    verify(timer).schedule(any(TimerTask.class), eq(expectedDelay));
  }

  @Test
  public void schedulingTask_withNegativeDelay_shouldNotThrow() {
    subject.schedule(runnable, createPastDateTime());
  }

  @Test
  public void schedulingTask_shouldWrapRunnableProperly() {
    String taskId = subject.schedule(runnable, SCHEDULED_PROCESSING_DATE_TIME);

    tasks.get(taskId).run();

    verify(runnable).run();
  }

  @Test
  public void schedulingTask_withErrorThrowingTask_shouldNotThrow() {
    Mockito.doThrow(new RuntimeException()).when(runnable).run();

    String taskId = subject.schedule(runnable, SCHEDULED_PROCESSING_DATE_TIME);

    tasks.get(taskId).run();
  }

  @Test
  public void cancellingTask_shouldCancelTask() {
    subject =
        new JavaTimerTaskScheduler(
            timer,
            new HashMap<String, TimerTask>() {
              {
                put(TASK_ID, timerTask);
              }
            },
            CLOCK_PROVIDER);

    subject.cancel(TASK_ID);

    verify(timerTask).cancel();
  }

  @Test
  public void cancellingTask_withNotExistingTask_shouldNotThrow() {
    subject.cancel(ANOTHER_TASK_ID);
  }
}
