package ca.ulaval.glo4003.shared.infrastructure.threading;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.application.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

import java.util.*;

import static java.time.ZoneOffset.UTC;

public class JavaTimerTaskScheduler implements TaskScheduler {
  private Timer timer;
  private Map<String, TimerTask> tasks;
  private ClockProvider clockProvider;

  public JavaTimerTaskScheduler() {
    this(new Timer(), new HashMap<>(), ServiceLocator.resolve(ClockProvider.class));
  }

  public JavaTimerTaskScheduler(
      Timer timer, Map<String, TimerTask> tasks, ClockProvider clockProvider) {
    this.timer = timer;
    this.tasks = tasks;
    this.clockProvider = clockProvider;
  }

  @Override
  public String schedule(Runnable runnable, DateTime scheduledProcessingDateTime) {
    String taskId = UUID.randomUUID().toString();
    TimerTask timerTask = createTimerTask(runnable);
    tasks.put(taskId, timerTask);
    long delay = computeDelay(scheduledProcessingDateTime);
    timer.schedule(timerTask, delay);
    return taskId;
  }

  private TimerTask createTimerTask(Runnable runnable) {
    return new TimerTask() {
      @Override
      public void run() {
        try {
          runnable.run();
        } catch (Throwable e) {
          // TODO: Log Error
          // e.printStackTrace();
        }
      }
    };
  }

  private long computeDelay(DateTime scheduledProcessingDateTime) {
    long currentEpochMilli =
        DateTime.now(clockProvider.getClock()).getValue().toInstant(UTC).toEpochMilli();
    long scheduledEpochMilli = scheduledProcessingDateTime.getValue().toInstant(UTC).toEpochMilli();
    return scheduledEpochMilli - currentEpochMilli;
  }

  @Override
  public void cancel(String taskId) {
    Optional.ofNullable(tasks.remove(taskId)).map(TimerTask::cancel);
  }
}
