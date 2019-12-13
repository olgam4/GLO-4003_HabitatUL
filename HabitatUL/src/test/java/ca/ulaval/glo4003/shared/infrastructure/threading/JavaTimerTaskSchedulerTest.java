package ca.ulaval.glo4003.shared.infrastructure.threading;

import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPastDateTime;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;
import static java.time.ZoneOffset.UTC;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JavaTimerTaskSchedulerTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final DateTime SCHEDULED_PROCESSING_DATE_TIME =
      DateTime.now(CLOCK_PROVIDER.getClock()).plus(Duration.ofMinutes(1));
  private static final String TASK_KEY = Faker.instance().internet().uuid();
  private static final String ANOTHER_TASK_KEY = Faker.instance().internet().uuid();

  @Mock private Timer timer;
  @Mock private Runnable runnable;
  @Mock private TimerTask timerTask;
  @Mock private Logger logger;

  private JavaTimerTaskScheduler subject;
  private Map<Comparable, TimerTask> tasks;

  @Before
  public void setUp() {
    tasks = new HashMap<>();
    subject = new JavaTimerTaskScheduler(timer, tasks, CLOCK_PROVIDER, logger);
  }

  @Test
  public void schedulingTask_shouldComputeDelay() {
    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);

    long expectedDelay =
        SCHEDULED_PROCESSING_DATE_TIME.getValue().toInstant(UTC).toEpochMilli()
            - DateTime.now(CLOCK_PROVIDER.getClock()).getValue().toInstant(UTC).toEpochMilli();
    verify(timer).schedule(any(TimerTask.class), eq(expectedDelay));
  }

  @Test
  public void schedulingTask_withNegativeDelay_shouldNotThrow() {
    subject.schedule(TASK_KEY, runnable, createPastDateTime());
  }

  @Test
  public void schedulingTask_shouldWrapRunnableProperly() {
    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);

    tasks.get(TASK_KEY).run();

    verify(runnable).run();
  }

  @Test
  public void schedulingTask_shouldRemoveTaskAfterCompletion() {
    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);

    tasks.get(TASK_KEY).run();

    assertFalse(tasks.containsKey(TASK_KEY));
  }

  @Test
  public void schedulingTask_withErrorThrowingTask_shouldNotThrow() {
    doThrow(new RuntimeException()).when(runnable).run();

    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);

    tasks.get(TASK_KEY).run();
  }

  @Test
  public void schedulingTask_withErrorThrowingTask_shouldRemoveTaskAfterCompletion() {
    doThrow(new RuntimeException()).when(runnable).run();
    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);

    tasks.get(TASK_KEY).run();

    assertFalse(tasks.containsKey(TASK_KEY));
  }

  @Test
  public void schedulingTask_withErrorThrowingTask_shouldLogErrorAsSevere() {
    doThrow(RuntimeException.class).when(runnable).run();

    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);
    tasks.get(TASK_KEY).run();

    verify(logger).severe(anyString());
  }

  @Test
  public void schedulingTask_shouldLogTaskStartAndEndAsInfo() {
    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);
    tasks.get(TASK_KEY).run();

    verify(logger, times(2)).info(contains(TASK_KEY));
  }

  @Test
  public void cancelingTask_shouldCancelTask() {
    subject =
        new JavaTimerTaskScheduler(
            timer,
            new HashMap<Comparable, TimerTask>() {
              {
                put(TASK_KEY, timerTask);
              }
            },
            CLOCK_PROVIDER,
            logger);

    subject.cancel(TASK_KEY);

    verify(timerTask).cancel();
  }

  @Test
  public void cancelingTask_withNotExistingTask_shouldNotThrow() {
    subject.cancel(ANOTHER_TASK_KEY);
  }
}
