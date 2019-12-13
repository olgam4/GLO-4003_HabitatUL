package ca.ulaval.glo4003.shared.application.threading;

import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.createDateTime;
import static org.mockito.Mockito.contains;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TaskSchedulerLoggingDecoratorTest {
  private static final DateTime SCHEDULED_PROCESSING_DATE_TIME = createDateTime();
  private static final String TASK_KEY = Faker.instance().internet().uuid();

  @Mock private TaskScheduler taskScheduler;
  @Mock private Logger logger;
  @Mock private Runnable runnable;

  private TaskSchedulerLoggingDecorator subject;

  @Before
  public void setUp() {
    subject = new TaskSchedulerLoggingDecorator(taskScheduler, logger);
  }

  @Test
  public void schedulingTask_shouldLogParamsAsInfo() {
    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);

    verify(logger).info(contains(TASK_KEY));
  }

  @Test
  public void schedulingTask_shouldDelegateToTaskScheduler() {
    subject.schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);

    verify(taskScheduler).schedule(TASK_KEY, runnable, SCHEDULED_PROCESSING_DATE_TIME);
  }

  @Test
  public void cancelingTask_shouldLogParamsAsInfo() {
    subject.cancel(TASK_KEY);

    verify(logger).info(contains(TASK_KEY));
  }

  @Test
  public void cancelingTask_shouldDelegateToTaskScheduler() {
    subject.cancel(TASK_KEY);

    verify(taskScheduler).cancel(TASK_KEY);
  }
}
